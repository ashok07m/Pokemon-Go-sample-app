package com.ashok.pokemongo.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ashok.domain.entity.PokemonModel
import com.ashok.domain.interactor.GetPokemonListUseCase
import com.ashok.pokemongo.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : BaseViewModel() {

    lateinit var pagingDataFlow: Flow<PagingData<PokemonModel>>

    fun loadPokemonList() {
        if (!::pagingDataFlow.isInitialized) {
            pagingDataFlow = getPokemonListUseCase.invoke().cachedIn(viewModelScope)
        }
    }
}