package com.ashok.pokemongo.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
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

    private lateinit var pokemonListAdapter: PokemonListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonListAdapter = PokemonListAdapter(::onItemClicked)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            pokemonList.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = pokemonListAdapter
            }

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    pokemonListAdapter.loadStateFlow.collectLatest { loadStates ->
                        Log.e("TAG", " load state : $loadStates")
                        when (val result = loadStates.refresh) {
                            is LoadState.Loading -> {
                                viewProgress.progressBar.visibility = View.VISIBLE
                            }
                            is LoadState.NotLoading -> {
                                handleNotLoadingState(loadStates)
                            }
                            is LoadState.Error -> {
                                showError(result.error as Exception)
                            }
                        }
                    }
                }
            }
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.pagingDataFlow.collectLatest {
                        pokemonListAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                    }
                }
            }
        }
    }

    /**
     * Dismiss loading bar
     */
    private fun dismissLoading() {
        binding.viewProgress.progressBar.visibility = View.GONE
    }

    /**
     * Handles not loading state for loading/error cases
     */
    private fun handleNotLoadingState(loadStates: CombinedLoadStates) {
        val result = loadStates.append
        if (result is LoadState.Error) {
            if (result.error is java.lang.Exception) {
                showError(result.error as Exception)
            }
        } else if (result is LoadState.Loading) {
            binding.viewProgress.progressBar.visibility = View.VISIBLE
        } else {
            dismissLoading()
        }
    }

    /**
     * Shows API error result to user with retry option
     */
    private fun showError(e: Exception) {
        dismissLoading()
        val errorMessage =
            viewModel.getNetworkErrorMessage(e)
        showError(
            view = binding.coordinatorLayout,
            message = errorMessage,
            action = {
                pokemonListAdapter.retry()
            })
    }

    /**
     * Navigates to [DetailsFragment]
     */
    private fun onItemClicked(pokemonModel: PokemonModel) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(pokemonModel.id)
        findNavController().navigate(action)
    }
}