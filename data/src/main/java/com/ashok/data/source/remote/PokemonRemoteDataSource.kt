package com.ashok.data.source.remote

import com.ashok.data.entity.PokemonEntity
import com.ashok.domain.ApiResult

interface PokemonRemoteDataSource {
    suspend fun fetchPokemonCollection(): ApiResult<PokemonEntity>
    suspend fun getPokemonImageUrl(id: Int): String
}