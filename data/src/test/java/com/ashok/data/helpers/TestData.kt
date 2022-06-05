package com.ashok.data.helpers

import com.ashok.data.entity.response.evolutionchain.Chain
import com.ashok.data.entity.response.evolutionchain.EvolvesTo
import com.ashok.data.entity.response.evolutionchain.PokemonEvolutionChainEntity
import com.ashok.data.entity.response.evolutionchain.Species
import com.ashok.data.entity.response.pokemoncollection.PokemonEntity
import com.ashok.data.entity.response.pokemoncollection.Result
import com.ashok.data.entity.response.pokemondetail.PokemonDetailEntity
import com.ashok.domain.entity.PokemonDetailModel
import com.ashok.domain.entity.PokemonEvolutionModel
import com.ashok.domain.entity.PokemonModel
import com.ashok.domain.exception.NoConnectivityException
import java.io.IOException

object TestData {
    val otherException = IOException("server error occurred")

    val pokemonModel: PokemonModel
        get() = PokemonModel(1, "pokemon", "", "")
    val pokemonDetailModel: PokemonDetailModel
        get() = PokemonDetailModel(1, "pokemon1", "", "", captureRate = 1, flavorText = emptyList())
    val pokemonEvolutionModel: PokemonEvolutionModel
        get() = PokemonEvolutionModel(3, "pokemon2", "", "", captureRate = 2)

    val result: Result
        get() = Result(name = "pokemon", url = "")

    val pokemonDetailEntity: PokemonDetailEntity
        get() = PokemonDetailEntity(name = "pokemon1")

    val pokemonEntity: PokemonEntity
        get() = PokemonEntity(results = listOf(result))

    val pokemonEvolutionChainEntity: PokemonEvolutionChainEntity
        get() = PokemonEvolutionChainEntity(
            id = 3,
            chain = Chain(evolvesTo = listOf(EvolvesTo(species = Species(name = "pokemon2"))))
        )

}