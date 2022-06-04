package com.ashok.pokemongo.ui.base

sealed class ViewStateResult {
    data class Loading(var isLoading: Boolean) : ViewStateResult()
    data class Error(var errorMsg: Int) : ViewStateResult()
    data class Success<T>(var data: T) : ViewStateResult()
}