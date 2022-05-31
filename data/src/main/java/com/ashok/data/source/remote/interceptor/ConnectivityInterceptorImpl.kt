package com.ashok.data.source.remote.interceptor

import com.ashok.domain.exception.NoConnectivityException
import com.ashok.data.util.NetworkHandler
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityInterceptorImpl @Inject constructor(private val networkHandler: NetworkHandler) :
    ConnectivityInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkHandler.isNetworkAvailable()) throw NoConnectivityException()
        return chain.proceed(chain.request())
    }
}