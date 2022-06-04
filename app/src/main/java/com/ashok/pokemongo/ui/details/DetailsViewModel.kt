package com.ashok.pokemongo.ui.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ashok.domain.ApiResult
import com.ashok.domain.interactor.GetPokemonDetailsUseCase
import com.ashok.domain.interactor.GetPokemonEvolutionChainUseCase
import com.ashok.pokemongo.R
import com.ashok.pokemongo.ui.base.BaseViewModel
import com.ashok.pokemongo.ui.base.ViewStateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
    private val getPokemonEvolutionChainUseCase: GetPokemonEvolutionChainUseCase
) : BaseViewModel(appContext) {

    private val _pokemonDetails: MutableLiveData<ViewStateResult> = MutableLiveData()
    val pokemonDetails: LiveData<ViewStateResult> = _pokemonDetails

    private val _evolutionChainInfo: MutableLiveData<ViewStateResult> = MutableLiveData()
    val evolutionChainInfo: LiveData<ViewStateResult> = _evolutionChainInfo

    private var captureRate1 = 0
    private var captureRate2 = 0

    fun getPokemonDetails(id: Int) = viewModelScope.launch {
        val response = getApiResponse { getPokemonDetailsUseCase.invoke(id) }
        response.collect { result ->
            when (result) {
                is ApiResult.Success -> {
                    result.suspendMap {
                        captureRate1 = it.captureRate
                        _pokemonDetails.value = ViewStateResult.Success(it)
                    }
                }
                is ApiResult.Error -> {
                    val errorMessage = getNetworkErrorMessage(result.exception)
                    _pokemonDetails.value = ViewStateResult.Error(errorMessage)
                }
            }
        }
    }

    fun getPokemonEvolutionInfo(id: Int) = viewModelScope.launch {
        val response = getApiResponse { getPokemonEvolutionChainUseCase.invoke(id) }
        response.collect { result ->
            when (result) {
                is ApiResult.Success -> {
                    _evolutionChainInfo.value = ViewStateResult.Success(result.data)
                    captureRate2 = result.data.captureRate
                    // get capture rate diff
                    val diff = getCaptureDifference(captureRate1, captureRate2)
                    _evolutionChainInfo.value = ViewStateResult.Success(diff)
                }
                is ApiResult.Error -> {
                    val errorMessage = getNetworkErrorMessage(result.exception)
                    _evolutionChainInfo.value = ViewStateResult.Error(errorMessage)
                }
            }
        }
    }

    private fun getCaptureDifference(value1: Int, value2: Int): Pair<Int, Int> {
        val diff = value1 - value2
        return if (diff >= 0) {
            Pair(diff, R.color.green)
        } else {
            Pair(diff, R.color.red)
        }
    }
}