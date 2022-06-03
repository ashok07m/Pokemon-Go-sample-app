package com.ashok.domain.repository

import com.ashok.domain.ApiResult
import com.ashok.domain.entity.PokemonDetailModel
import com.ashok.domain.entity.PokemonEvolutionModel
import com.ashok.domain.entity.PokemonModel
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun fetchPokemonCollection(): Flow<ApiResult<List<PokemonModel>>>
    suspend fun fetchPokemonDetail(id: Int): Flow<ApiResult<PokemonDetailModel>>
    suspend fun fetchPokemonEvolutionChain(id: Int): Flow<ApiResult<PokemonEvolutionModel>>
}