package com.ashok.domain.entity

import com.ashok.domain.helpers.BaseTest
import org.junit.Assert
import org.junit.Test

class PokemonModelTest : BaseTest() {
    private lateinit var pokemonModel: PokemonModel

    private val name = "Bulbasaur"

    override fun setup() {
        super.setup()
        pokemonModel = PokemonModel(1, name, "", "")
    }

    @Test
    fun `test that object constructed successfully`() {
        val actualName = pokemonModel.name
        Assert.assertEquals(actualName, name)
    }
}