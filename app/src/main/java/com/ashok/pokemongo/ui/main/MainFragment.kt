package com.ashok.pokemongo.ui.main

import android.os.Bundle
import android.view.View
import com.ashok.pokemongo.ui.base.ViewStateResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ashok.domain.entity.PokemonModel
import com.ashok.pokemongo.databinding.FragmentMainBinding
import com.ashok.pokemongo.ui.adapters.PokemonListAdapter
import com.ashok.pokemongo.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonListAdapter = PokemonListAdapter(::onItemClicked)
        viewModel.getPokemonList()
        with(binding) {
            swipeRefresh.setOnRefreshListener { getPokemonList() }

            pokemonList.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = pokemonListAdapter
            }

            viewModel.pokemonListResult.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ViewStateResult.Success<*> -> {
                        val data = state.data
                        if (data is List<*>) {
                            pokemonListAdapter.submitList(data as List<PokemonModel>)
                        }
                    }
                    is ViewStateResult.Error -> {
                        showError(
                            view = binding.coordinatorLayout,
                            message = state.errorMsg,
                            action = { getPokemonList() })
                    }
                    else -> {}
                }
            }
            viewModel.viewLoadingStateResult.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ViewStateResult.Loading -> {
                        if (state.isLoading) {
                            viewProgress.progressBar.visibility = View.VISIBLE
                        } else {
                            swipeRefresh.isRefreshing = false
                            viewProgress.progressBar.visibility = View.GONE
                        }
                    }
                    else -> {}
                }
            }
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
        viewModel.getPokemonList(isForceFetch = true)
    }
}