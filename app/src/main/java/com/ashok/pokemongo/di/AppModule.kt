package com.ashok.pokemongo.di

import com.ashok.domain.interactor.GetPokemonList
import com.ashok.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideGetPokemonListUseCase(pokemonRepository: PokemonRepository): GetPokemonList {
        return GetPokemonList(pokemonRepository)
    }
}