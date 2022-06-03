package com.ashok.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

sealed class ApiResult<T>(
    data: T? = null,
    exception: Exception? = null
) {
    data class Success<T>(val data: T) : ApiResult<T>(data, null)

    data class Error<T>(
        val exception: Exception
    ) : ApiResult<T>(null, exception)

    suspend inline fun <R : Any> suspendMap(transform: suspend (T) -> R): ApiResult<R> {
        return when (this) {
            is Error -> Error(this.exception)
            is Success -> Success(transform(this.data))
        }
    }
}