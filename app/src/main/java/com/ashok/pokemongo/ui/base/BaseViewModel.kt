package com.ashok.pokemongo.ui.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ashok.domain.ApiResult
import com.ashok.domain.exception.NoConnectivityException
import com.ashok.pokemongo.R
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel : ViewModel() {

    private val _viewLoadingStateResult = MutableLiveData<ViewStateResult>()
    val viewLoadingStateResult: LiveData<ViewStateResult> = _viewLoadingStateResult


    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    suspend fun <T> getApiResponse(
        call: suspend () -> Flow<ApiResult<T>>
    ): Flow<ApiResult<T>> {
        _viewLoadingStateResult.value = ViewStateResult.Loading(true)
        var response = call.invoke()
        _viewLoadingStateResult.value = ViewStateResult.Loading(false)
        return response
    }

    fun getNetworkErrorMessage(exception: Exception): Int {
        val errorMessage = if (exception is NoConnectivityException) {
            R.string.error_no_internet
        } else {
            R.string.error_unable_to_load
        }
        return errorMessage
    }
}