package com.ashok.data.source.remote

import com.ashok.data.di.IoDispatcher
import com.ashok.data.entity.response.evolutionchain.PokemonEvolutionChainEntity
import com.ashok.data.entity.response.pokemoncollection.PokemonEntity
import com.ashok.data.entity.response.pokemondetail.PokemonDetailEntity
import com.ashok.data.source.api.PokemonApiService
import com.ashok.domain.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class PokemonRemoteDataSourceImpl @Inject constructor(
    private val pokemonApiService: PokemonApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : PokemonRemoteDataSource, BaseDataSource(ioDispatcher) {

    override suspend fun fetchPokemonCollection(): ApiResult<PokemonEntity> {
        return executeRequest { pokemonApiService.getPokemonSpecies() }
    }

    override suspend fun fetchPokemonDetail(id: Int): ApiResult<PokemonDetailEntity> {
        return executeRequest { pokemonApiService.getPokemonDetail(id) }
    }

    override suspend fun fetchPokemonEvolutionChain(id: Int): ApiResult<PokemonEvolutionChainEntity> {
        return executeRequest { pokemonApiService.getEvolutionChain(id) }
    }

}