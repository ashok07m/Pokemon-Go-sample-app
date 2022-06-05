package com.ashok.pokemongo.helpers

import com.ashok.domain.entity.PokemonDetailModel
import com.ashok.domain.entity.PokemonEvolutionModel
import com.ashok.domain.entity.PokemonModel
import com.ashok.domain.exception.NoConnectivityException
import java.io.IOException

object TestData {
    val networkException = NoConnectivityException()
    val otherException = IOException("server error occurred")
    val pokemonModel: PokemonModel
        get() = PokemonModel(1, "pokemon", "", "")
    val pokemonDetailModel: PokemonDetailModel
        get() = PokemonDetailModel(1, "pokemon1", "", "", captureRate = 1, flavorText = emptyList())
    val pokemonEvolutionModel: PokemonEvolutionModel
        get() = PokemonEvolutionModel(3, "pokemon2", "", "", captureRate = 2)
}