package com.ashok.pokemongo.ui.details

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashok.domain.entity.PokemonDetailModel
import com.ashok.domain.entity.PokemonEvolutionModel
import com.ashok.pokemongo.databinding.FragmentDetailsBinding
import com.ashok.pokemongo.ui.adapters.DescriptionListAdapter
import com.ashok.pokemongo.ui.base.BaseFragment
import com.ashok.pokemongo.ui.base.ViewStateResult
import com.ashok.pokemongo.ui.utils.AppUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private val viewModel: DetailsViewModel by viewModels()
    private val safeArgs: DetailsFragmentArgs by navArgs()
    private val pokemonId: Int by lazy { safeArgs.pokemonId }
    private lateinit var descriptionLisAdapter: DescriptionListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        descriptionLisAdapter = DescriptionListAdapter(emptyList())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFlavorTextList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = descriptionLisAdapter
        }

        with(viewModel) {
            binding.group1.visibility = View.GONE
            binding.group2.visibility = View.GONE

            fetchPokemonDetails()

            viewLoadingStateResult.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ViewStateResult.Loading -> {
                        if (state.isLoading) {
                            binding.viewProgress.progressBar.visibility = View.VISIBLE
                        } else {
                            binding.viewProgress.progressBar.visibility = View.GONE
                        }
                    }
                    else -> {}
                }
            }

            pokemonDetails.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ViewStateResult.Success<*> -> {
                        when (val data = state.data) {
                            is PokemonDetailModel -> {
                                fetchPokemonEvolutionInfo()
                                showPokemonDetails(data)
                            }
                        }
                    }
                    is ViewStateResult.Error -> {
                        showError(
                            view = binding.coordinatorLayout,
                            message = state.errorMsg,
                            action = { fetchPokemonDetails() })
                    }
                }
            }

            evolutionChainInfo.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ViewStateResult.Success<*> -> {
                        when (val data = state.data) {
                            is PokemonEvolutionModel -> {
                                showEvolutionInfo(data)
                            }
                            is Pair<*, *> -> {
                                showCaptureRateDiff(data)
                            }
                        }
                    }
                    is ViewStateResult.Error -> {
                        showError(
                            view = binding.coordinatorLayout,
                            message = state.errorMsg,
                            action = { fetchPokemonEvolutionInfo() })
                    }
                }
            }
        }
    }

    private fun showCaptureRateDiff(data: Pair<*, *>) {
        binding.txtCaptureRateValue.apply {
            text = data.first.toString()
            val color = ContextCompat.getColor(context, data.second as Int)
            setTextColor(color)
        }
    }

    private fun showEvolutionInfo(data: PokemonEvolutionModel) {
        with(binding) {
            txtPokemon2Name.text = data.name
            AppUtil.loadImageFromUri(data.imgUrl, ivPokemon2)
            group2.visibility = View.VISIBLE
        }
    }

    private fun showPokemonDetails(data: PokemonDetailModel) {
        with(binding) {
            txtPokemon1Name.text = data.name
            AppUtil.loadImageFromUri(data.imgUrl, ivPokemon1)
            descriptionLisAdapter.submitList(data.flavorText)
            group1.visibility = View.VISIBLE
        }
    }

    private fun fetchPokemonDetails() {
        viewModel.getPokemonDetails(pokemonId)
    }

    private fun fetchPokemonEvolutionInfo() {
        viewModel.getPokemonEvolutionInfo(pokemonId)
    }

}