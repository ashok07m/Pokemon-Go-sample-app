package com.ashok.domain.repository

import androidx.paging.PagingData
import com.ashok.domain.ApiResult
import com.ashok.domain.entity.PokemonDetailModel
import com.ashok.domain.entity.PokemonEvolutionModel
import com.ashok.domain.entity.PokemonModel
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun fetchPokemonDetail(id: Int): Flow<ApiResult<PokemonDetailModel>>
    suspend fun fetchPokemonEvolutionChain(id: Int): Flow<ApiResult<PokemonEvolutionModel>>
    fun loadPokemonList(): Flow<PagingData<PokemonModel>>
}