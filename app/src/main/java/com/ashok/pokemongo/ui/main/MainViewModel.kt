package com.ashok.pokemongo.ui.main

import android.content.Context
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

    init {
        getPokemonList()
    }

    fun getPokemonList(isForceFetch: Boolean = false) =
        viewModelScope.launch {
            if (viewStateResult.value !is ViewStateResult.Success<*> || isForceFetch) {
                val response = getApiResponse { getPokemonListUseCase.invoke() }
                response.collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            _viewStateResult.value = ViewStateResult.Success(result.data)
                        }
                        is ApiResult.Error -> {
                            val errorMessage = getNetworkErrorMessage(result.exception)
                            _viewStateResult.value = ViewStateResult.Error(errorMessage)
                        }
                    }
                }
            }
        }
}