package com.ashok.data.di

import com.ashok.data.repository.PokemonRepositoryImpl
import com.ashok.data.source.remote.PokemonRemoteDataSource
import com.ashok.data.source.remote.PokemonRemoteDataSourceImpl
import com.ashok.data.source.remote.interceptor.ConnectivityInterceptor
import com.ashok.data.source.remote.interceptor.ConnectivityInterceptorImpl
import com.ashok.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityInterceptor(impl: ConnectivityInterceptorImpl): ConnectivityInterceptor

    @Binds
    @Singleton
    abstract fun bindGitReposRemoteSource(impl: PokemonRemoteDataSourceImpl): PokemonRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindBookmarksRepository(impl: PokemonRepositoryImpl): PokemonRepository


}