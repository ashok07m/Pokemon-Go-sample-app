package com.ashok.data.source.api

import com.ashok.data.entity.PokemonEntity
import retrofit2.Response
import retrofit2.http.GET

interface PokemonApiService {
    companion object {
        const val BASE_URL = "https://pokeapi.co"
        const val ENDPOINT_POKEMON_SPECIES = "/api/v2/pokemon-species"
        private const val POKEMON_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/%s.png"

        fun getPokemonImageUrlForId(id: Int) = String.format(POKEMON_IMAGE_URL, id)

    }

    @GET(ENDPOINT_POKEMON_SPECIES)
    suspend fun getPokemonSpecies(): Response<PokemonEntity>
}