package com.ashok.data.util

import com.ashok.data.helpers.BaseTest
import org.junit.Assert
import org.junit.Test

class DataUtilTest : BaseTest() {
    companion object {
        const val FAKE_POKEMON_ID = 1
        const val FAKE_FALLBACK_ID = 0
        const val FAKE_POKEMON_INFO_URL = "https://pokeapi.co/api/v2/pokemon-species/1/"
        const val FAKE_WRONG_POKEMON_INFO_URL = "https://pokeapi.co/api/v2/pokemon-species/"
        const val FAKE_POKEMON_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${FAKE_POKEMON_ID}.png"
        const val FAKE_INFO_URL = "/api/v2/pokemon-species/${FAKE_POKEMON_ID}/"
    }

    @Test
    fun `verify getPokemonIdFromUrl() returns the valid pokemon id`() {
        val actualId = DataUtil.getPokemonIdFromUrl(FAKE_POKEMON_INFO_URL)
        Assert.assertEquals(actualId, FAKE_POKEMON_ID)
    }

    @Test
    fun `verify getPokemonIdFromUrl() returns the default pokemon id`() {
        val actualId = DataUtil.getPokemonIdFromUrl(FAKE_WRONG_POKEMON_INFO_URL)
        Assert.assertEquals(actualId, FAKE_FALLBACK_ID)
    }

    @Test
    fun `verify getPokemonImageUrlForId() returns the correct formatted url`() {
        val actualUrl = DataUtil.getPokemonImageUrlForId(FAKE_POKEMON_ID)
        Assert.assertEquals(actualUrl, FAKE_POKEMON_IMAGE_URL)
    }

    @Test
    fun `verify getPokemonInfoUrlForId() returns the correct formatted url`() {
        val actualUrl = DataUtil.getPokemonInfoUrlForId(FAKE_POKEMON_ID)
        Assert.assertEquals(actualUrl, FAKE_INFO_URL)
    }
}