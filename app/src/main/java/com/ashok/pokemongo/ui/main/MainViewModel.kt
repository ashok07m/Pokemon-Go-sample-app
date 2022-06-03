package com.ashok.pokemongo.ui.main

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ashok.domain.entity.PokemonModel
import com.ashok.domain.interactor.GetPokemonListUseCase
import com.ashok.pokemongo.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val getPokemonListUseCase: GetPokemonListUseCase
) : BaseViewModel(appContext) {

    fun loadPokemonList(): Flow<PagingData<PokemonModel>> =
        getPokemonListUseCase.invoke().cachedIn(viewModelScope)
}