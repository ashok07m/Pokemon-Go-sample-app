package com.ashok.domain

sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()

    data class Error<T>(
        val exception: Exception
    ) : ApiResult<T>()

    suspend inline fun <R : Any> suspendMap(transform: suspend (T) -> R): ApiResult<R> {
        return when (this) {
            is Error -> Error(this.exception)
            is Success -> Success(transform(this.data))
        }
    }
}