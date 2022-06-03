package com.ashok.data.extension

import com.ashok.domain.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform


suspend inline fun <T : Any, R : Any> Flow<ApiResult<T>>.mapState(crossinline transform: suspend (value: T) -> R): Flow<ApiResult<R>> =
    transform { value ->
        return@transform emit(value.suspendMap(transform))
    }