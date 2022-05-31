package com.ashok.data.source.remote

import com.ashok.domain.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

abstract class BaseDataSource(
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun <T> executeRequest(request: suspend () -> Response<T>): ApiResult<T> {
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
}