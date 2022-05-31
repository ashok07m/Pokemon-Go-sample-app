package com.ashok.domain.repository

import com.ashok.domain.ApiResult
import com.ashok.domain.entity.PokemonModel
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun fetchPokemonCollection(): Flow<ApiResult<List<PokemonModel>>>
}