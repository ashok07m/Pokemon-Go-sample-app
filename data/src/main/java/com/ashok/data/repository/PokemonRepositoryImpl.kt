package com.ashok.data.repository

import com.ashok.data.di.DefaultDispatcher
import com.ashok.data.entity.mapper.toPokemonDetailModel
import com.ashok.data.entity.mapper.toPokemonEvolutionModel
import com.ashok.data.entity.mapper.toPokemonModel
import com.ashok.data.extension.mapState
import com.ashok.data.source.remote.PokemonRemoteDataSource
import com.ashok.domain.ApiResult
import com.ashok.domain.entity.PokemonDetailModel
import com.ashok.domain.entity.PokemonEvolutionModel
import com.ashok.domain.entity.PokemonModel
import com.ashok.domain.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSource,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : PokemonRepository {

    override suspend fun fetchPokemonCollection(): Flow<ApiResult<List<PokemonModel>>> {
        return flowOf(remoteDataSource.fetchPokemonCollection().suspendMap { pokemonEntity ->
            pokemonEntity.results?.map { result ->
                transformObject { result.toPokemonModel() }
            } ?: emptyList()
        })
    }

    override suspend fun fetchPokemonDetail(id: Int): Flow<ApiResult<PokemonDetailModel>> {
        return flowOf(remoteDataSource.fetchPokemonDetail(id).suspendMap { pokemonDetailEntity ->
            transformObject { pokemonDetailEntity.toPokemonDetailModel() }
        })
    }

    override suspend fun fetchPokemonEvolutionChain(id: Int): Flow<ApiResult<PokemonEvolutionModel>> {
        return flowOf(
            remoteDataSource.fetchPokemonEvolutionChain(id).suspendMap { evolutionChainEntity ->
                transformObject {
                    val model = evolutionChainEntity.toPokemonEvolutionModel()
                    fetchPokemonDetail(model.id).mapState { pokemon ->
                        model.captureRate = pokemon.captureRate
                    }.collect()
                    model
                }
            }
        )
    }

    private suspend fun <T> transformObject(call: suspend () -> T): T {
        return withContext(defaultDispatcher) {
            call.invoke()
        }
    }
}