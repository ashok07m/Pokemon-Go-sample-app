package com.ashok.pokemongo.ui.base

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ashok.domain.ApiResult
import com.ashok.pokemongo.R
import com.ashok.pokemongo.ui.utils.AppUtil.DELAY_INTERVAL
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.io.IOException

abstract class BaseViewModel(
    private val appContext: Context,
) : ViewModel() {

    protected val _viewStateResult = MutableLiveData<ViewStateResult>()
    val viewStateResult: LiveData<ViewStateResult> = _viewStateResult


    protected suspend fun <T> getApiResponse(
        call: suspend () -> Flow<ApiResult<T>>
    ): Flow<ApiResult<T>> {
        _viewStateResult.value = ViewStateResult.Loading(true)
        delay(DELAY_INTERVAL) // temporary network delay
        var response = call.invoke()
        _viewStateResult.value = ViewStateResult.Loading(false)
        return response
    }

    protected fun getNetworkErrorMessage(exception: Exception): String {
        val errorMessage = if (exception is IOException) {
            appContext.getString(R.string.error_no_internet)
        } else {
            appContext.getString(R.string.error_unable_to_load)
        }
        return errorMessage
    }
}