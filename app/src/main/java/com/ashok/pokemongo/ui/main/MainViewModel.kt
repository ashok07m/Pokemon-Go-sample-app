package com.ashok.pokemongo.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ashok.domain.ApiResult
import com.ashok.domain.interactor.GetPokemonListUseCase
import com.ashok.pokemongo.ui.base.BaseViewModel
import com.ashok.pokemongo.ui.base.ViewStateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val getPokemonListUseCase: GetPokemonListUseCase
) : BaseViewModel(appContext) {

    private val _pokemonListResult = MutableLiveData<ViewStateResult>()
    val pokemonListResult: LiveData<ViewStateResult> = _pokemonListResult

    init {
        getPokemonList()
    }

    fun getPokemonList(isForceFetch: Boolean = false) =
        viewModelScope.launch {
            if (_pokemonListResult.value !is ViewStateResult.Success<*> || isForceFetch) {
                val response = getApiResponse { getPokemonListUseCase.invoke() }
                response.collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            _pokemonListResult.value = ViewStateResult.Success(result.data)
                        }
                        is ApiResult.Error -> {
                            val errorMessage = getNetworkErrorMessage(result.exception)
                            _pokemonListResult.value = ViewStateResult.Error(errorMessage)
                        }
                    }
                }
            }
        }
}