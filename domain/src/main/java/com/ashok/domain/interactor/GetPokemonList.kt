package com.ashok.domain.interactor

import com.ashok.domain.ApiResult
import com.ashok.domain.entity.PokemonModel
import com.ashok.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonList @Inject constructor(private val pokemonRepository: PokemonRepository) {

    suspend operator fun invoke(): Flow<ApiResult<List<PokemonModel>>> {
        return pokemonRepository.fetchPokemonCollection()
    }

}