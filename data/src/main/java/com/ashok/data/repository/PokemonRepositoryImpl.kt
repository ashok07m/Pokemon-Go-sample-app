package com.ashok.data.repository

import androidx.annotation.VisibleForTesting
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ashok.data.di.DefaultDispatcher
import com.ashok.data.entity.mapper.toPokemonDetailModel
import com.ashok.data.entity.mapper.toPokemonEvolutionModel
import com.ashok.data.extension.mapState
import com.ashok.data.source.api.PokemonApiService
import com.ashok.data.source.remote.PokemonRemoteDataSourceImpl
import com.ashok.domain.ApiResult
import com.ashok.domain.entity.PokemonDetailModel
import com.ashok.domain.entity.PokemonEvolutionModel
import com.ashok.domain.entity.PokemonModel
import com.ashok.domain.repository.PokemonRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonRemoteDataSourceImpl,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : PokemonRepository {

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

    override fun loadPokemonList(): Flow<PagingData<PokemonModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PokemonApiService.PAGE_DEFAULT_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { remoteDataSource }
        ).flow
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    suspend fun <T> transformObject(call: suspend () -> T): T {
        return withContext(defaultDispatcher) {
            call.invoke()
        }
    }
}