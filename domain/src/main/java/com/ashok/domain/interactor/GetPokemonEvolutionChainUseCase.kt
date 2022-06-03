package com.ashok.domain.interactor

import com.ashok.domain.ApiResult
import com.ashok.domain.entity.PokemonEvolutionModel
import com.ashok.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonEvolutionChainUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {

    suspend operator fun invoke(id: Int): Flow<ApiResult<PokemonEvolutionModel>> {
        return pokemonRepository.fetchPokemonEvolutionChain(id)
    }

}