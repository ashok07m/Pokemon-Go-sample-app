package com.ashok.pokemongo.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.ashok.domain.entity.PokemonModel
import com.ashok.pokemongo.databinding.FragmentMainBinding
import com.ashok.pokemongo.ui.adapters.PokemonListAdapter
import com.ashok.pokemongo.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonListAdapter = PokemonListAdapter(::onItemClicked)
        with(binding) {
            swipeRefresh.setOnRefreshListener { getPokemonList() }

            pokemonList.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = pokemonListAdapter
            }

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    pokemonListAdapter.loadStateFlow.collectLatest { loadStates ->
                        when (val result = loadStates.refresh) {
                            is LoadState.Loading -> {
                                viewProgress.progressBar.visibility = View.VISIBLE
                            }
                            is LoadState.NotLoading -> {
                                dismissLoading()
                            }
                            is LoadState.Error -> {
                                dismissLoading()
                                val errorMessage =
                                    viewModel.getNetworkErrorMessage(Exception(result.error))
                                showError(
                                    view = binding.coordinatorLayout,
                                    message = errorMessage,
                                    action = { getPokemonList() })
                            }
                        }
                    }
                }
            }

            getPokemonList()
        }
    }

    /**
     * Navigates to [DetailsFragment]
     */
    private fun onItemClicked(pokemonModel: PokemonModel) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(pokemonModel.id)
        findNavController().navigate(action)
    }

    private fun getPokemonList() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadPokemonList().collectLatest {
                    (binding.pokemonList.adapter as PokemonListAdapter).submitData(it)
                }
            }
        }
    }

    private fun dismissLoading() {
        with(binding) {
            swipeRefresh.isRefreshing = false
            viewProgress.progressBar.visibility = View.GONE
        }
    }
}