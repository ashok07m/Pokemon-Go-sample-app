package com.ashok.data.source.remote

import com.ashok.data.entity.response.evolutionchain.PokemonEvolutionChainEntity
import com.ashok.data.entity.response.pokemoncollection.PokemonEntity
import com.ashok.data.entity.response.pokemondetail.PokemonDetailEntity
import com.ashok.domain.ApiResult

interface PokemonRemoteDataSource {
    suspend fun fetchPokemonCollection(offset: Int, limit: Int): ApiResult<PokemonEntity>
    suspend fun fetchPokemonDetail(id: Int): ApiResult<PokemonDetailEntity>
    suspend fun fetchPokemonEvolutionChain(id: Int): ApiResult<PokemonEvolutionChainEntity>
}