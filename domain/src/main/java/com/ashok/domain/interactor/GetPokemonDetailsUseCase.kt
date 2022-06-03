package com.ashok.domain.interactor

import com.ashok.domain.ApiResult
import com.ashok.domain.entity.PokemonDetailModel
import com.ashok.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonDetailsUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {

    suspend operator fun invoke(id: Int): Flow<ApiResult<PokemonDetailModel>> {
        return pokemonRepository.fetchPokemonDetail(id)
    }
}