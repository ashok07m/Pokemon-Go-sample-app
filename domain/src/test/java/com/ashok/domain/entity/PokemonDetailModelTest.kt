package com.ashok.domain.entity

import com.ashok.domain.helpers.BaseTest
import org.junit.Assert
import org.junit.Test

class PokemonDetailModelTest : BaseTest() {
    private lateinit var pokemonDetailModel: PokemonDetailModel

    private val name = "Bulbasaur"

    override fun setup() {
        super.setup()
        pokemonDetailModel = PokemonDetailModel(1, name, "", "", 0, emptyList())
    }

    @Test
    fun `test that object constructed successfully`() {
        val actualName = pokemonDetailModel.name
        Assert.assertEquals(actualName, name)
    }
}