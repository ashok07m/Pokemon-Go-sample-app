package com.ashok.data.entity.mapper

import com.ashok.data.helpers.BaseTest
import com.ashok.data.helpers.TestData
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonDataMapperTest : BaseTest() {


    @Test
    fun `verify Result transforms to PokemonModel successfully`() {
        val model = TestData.result.toPokemonModel()
        val actualName = model.name
        val expectedName = TestData.pokemonModel.name
        assertEquals(actualName, expectedName)
    }

    @Test
    fun `verify PokemonDetailEntity transforms to PokemonDetailModel successfully`() {
        val model = TestData.pokemonDetailEntity.toPokemonDetailModel()
        val actualName = model.name
        val expectedName = TestData.pokemonDetailModel.name
        assertEquals(actualName, expectedName)
    }

    @Test
    fun `verify PokemonEvolutionChainEntity transforms to PokemonEvolutionModel successfully`() {
        val model = TestData.pokemonEvolutionChainEntity.toPokemonEvolutionModel()
        val actualName = model.name
        val expectedName = TestData.pokemonEvolutionModel.name
        assertEquals(actualName, expectedName)
    }

}