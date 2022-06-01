package com.ashok.data.repository

import com.ashok.data.di.DefaultDispatcher
import com.ashok.data.entity.mapper.toPokemonModel
import com.ashok.data.source.remote.PokemonRemoteDataSource
import com.ashok.domain.ApiResult
import com.ashok.domain.entity.PokemonModel
import com.ashok.domain.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : PokemonRepository {

    override suspend fun fetchPokemonCollection(): Flow<ApiResult<List<PokemonModel>>> {
        return flowOf(remoteDataSource.fetchPokemonCollection().suspendMap { pokemonEntity ->
            pokemonEntity.results?.mapIndexed { index, result ->
                val updatedList = withContext(defaultDispatcher) {
                    val url = remoteDataSource.getPokemonImageUrl(index + 1)
                    result.toPokemonModel(url)
                }
                updatedList
            } ?: emptyList()
        })
    }
}