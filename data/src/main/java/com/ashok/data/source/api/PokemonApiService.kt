package com.ashok.data.source.api

import com.ashok.data.entity.response.evolutionchain.PokemonEvolutionChainEntity
import com.ashok.data.entity.response.pokemondetail.PokemonDetailEntity
import com.ashok.data.entity.response.pokemoncollection.PokemonEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    companion object {
        const val BASE_URL = "https://pokeapi.co"
        const val ENDPOINT_POKEMON_SPECIES = "/api/v2/pokemon-species"
        const val ENDPOINT_POKEMON_EVOLUTION_CHAIN = "/api/v2/evolution-chain"
        const val POKEMON_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/%s.png"
        const val PAGE_DEFAULT_SIZE = 20
        const val STARTING_OFFSET_VALUE = 0
    }

    @GET(ENDPOINT_POKEMON_SPECIES)
    suspend fun getPokemonSpecies(
        @Query("offset") offsetValue: Int = 0,
        @Query("limit") limitValue: Int = PAGE_DEFAULT_SIZE
    ): Response<PokemonEntity>

    @GET("$ENDPOINT_POKEMON_SPECIES/{id}/")
    suspend fun getPokemonDetail(@Path("id") id: Int): Response<PokemonDetailEntity>

    @GET("$ENDPOINT_POKEMON_EVOLUTION_CHAIN/{id}/")
    suspend fun getEvolutionChain(@Path("id") id: Int): Response<PokemonEvolutionChainEntity>
}