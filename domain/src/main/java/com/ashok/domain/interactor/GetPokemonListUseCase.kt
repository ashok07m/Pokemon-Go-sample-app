package com.ashok.domain.interactor

import androidx.paging.PagingData
import com.ashok.domain.entity.PokemonModel
import com.ashok.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {

    operator fun invoke(): Flow<PagingData<PokemonModel>> {
        return pokemonRepository.loadPokemonList()
    }

}