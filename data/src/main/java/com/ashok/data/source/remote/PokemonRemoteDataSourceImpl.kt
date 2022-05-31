package com.ashok.data.source.remote

import com.ashok.data.di.IoDispatcher
import com.ashok.data.entity.PokemonEntity
import com.ashok.data.source.api.PokemonApiService
import com.ashok.domain.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class PokemonRemoteDataSourceImpl @Inject constructor(
    private val pokemonApiService: PokemonApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : PokemonRemoteDataSource, BaseDataSource(ioDispatcher) {

    override suspend fun fetchPokemonCollection(): ApiResult<List<PokemonEntity>> {
        return executeRequest { pokemonApiService.getPokemonSpecies() }
    }

    override suspend fun getPokemonImageUrl(id: Int): String {
        return pokemonApiService.getPokemonImageUrlForId(id)
    }

}