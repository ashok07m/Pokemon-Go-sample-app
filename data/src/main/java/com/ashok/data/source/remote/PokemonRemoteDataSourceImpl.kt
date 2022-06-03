package com.ashok.data.source.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ashok.data.di.DefaultDispatcher
import com.ashok.data.di.IoDispatcher
import com.ashok.data.entity.mapper.toPokemonModel
import com.ashok.data.entity.response.evolutionchain.PokemonEvolutionChainEntity
import com.ashok.data.entity.response.pokemoncollection.PokemonEntity
import com.ashok.data.entity.response.pokemondetail.PokemonDetailEntity
import com.ashok.data.source.api.PokemonApiService
import com.ashok.data.source.api.PokemonApiService.Companion.STARTING_OFFSET_VALUE
import com.ashok.domain.ApiResult
import com.ashok.domain.entity.PokemonModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PokemonRemoteDataSourceImpl @Inject constructor(
    private val pokemonApiService: PokemonApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : PokemonRemoteDataSource, PagingSource<Int, PokemonModel>() {

    override suspend fun fetchPokemonCollection(offset: Int, limit: Int): ApiResult<PokemonEntity> {
        return executeRequest {
            pokemonApiService.getPokemonSpecies(
                offsetValue = offset,
                limitValue = limit
            )
        }
    }

    override suspend fun fetchPokemonDetail(id: Int): ApiResult<PokemonDetailEntity> {
        return executeRequest { pokemonApiService.getPokemonDetail(id) }
    }

    override suspend fun fetchPokemonEvolutionChain(id: Int): ApiResult<PokemonEvolutionChainEntity> {
        return executeRequest { pokemonApiService.getEvolutionChain(id) }
    }

    private suspend fun <T> executeRequest(request: suspend () -> Response<T>): ApiResult<T> {
        return try {
            withContext(ioDispatcher) {
                val response = request.invoke()
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { return@withContext ApiResult.Success(it) }
                }
                ApiResult.Error(exception = Exception("${response.code()} : ${response.message()}"))
            }
        } catch (e: Exception) {
            ApiResult.Error(exception = e)
        }
    }


    private suspend fun <T> transformObject(call: suspend () -> T): T {
        return withContext(defaultDispatcher) {
            call.invoke()
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonModel> {

        var position = params.key ?: STARTING_OFFSET_VALUE
        val response = fetchPokemonCollection(
            offset = position,
            limit = params.loadSize
        )

        return when (response) {
            is ApiResult.Success -> {
                val nextKey: Int? =
                    response.data.next?.split("?")?.get(1)?.split("&")?.get(0)?.split("=")
                        ?.get(1)
                        ?.toInt()
                val updatedMap: List<PokemonModel> = response.data.results?.map { result ->
                    transformObject { result.toPokemonModel() }
                } ?: emptyList()

                LoadResult.Page(
                    data = updatedMap,
                    prevKey = if (position == STARTING_OFFSET_VALUE) null else position - 1,
                    nextKey = nextKey
                )
            }

            is ApiResult.Error -> {
                LoadResult.Error(response.exception)
            }
        }
    }
}