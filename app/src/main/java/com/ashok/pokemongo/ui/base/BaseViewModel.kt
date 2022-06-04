package com.ashok.pokemongo.ui.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ashok.domain.ApiResult
import com.ashok.domain.exception.NoConnectivityException
import com.ashok.pokemongo.R
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel(
    private val appContext: Context,
) : ViewModel() {

    private val _viewLoadingStateResult = MutableLiveData<ViewStateResult>()
    val viewLoadingStateResult: LiveData<ViewStateResult> = _viewLoadingStateResult


    protected suspend fun <T> getApiResponse(
        call: suspend () -> Flow<ApiResult<T>>
    ): Flow<ApiResult<T>> {
        _viewLoadingStateResult.value = ViewStateResult.Loading(true)
        var response = call.invoke()
        _viewLoadingStateResult.value = ViewStateResult.Loading(false)
        return response
    }

    fun getNetworkErrorMessage(exception: Exception): String {
        val errorMessage = if (exception is NoConnectivityException) {
            appContext.getString(R.string.error_no_internet)
        } else {
            appContext.getString(R.string.error_unable_to_load)
        }
        return errorMessage
    }
}