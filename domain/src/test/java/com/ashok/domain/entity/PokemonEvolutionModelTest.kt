package com.ashok.domain.entity

import com.ashok.domain.helpers.BaseTest
import org.junit.Assert
import org.junit.Test

class PokemonEvolutionModelTest : BaseTest() {
    private lateinit var pokemonEvolutionModel: PokemonEvolutionModel

    private val name = "Bulbasaur"

    override fun setup() {
        super.setup()
        pokemonEvolutionModel = PokemonEvolutionModel(1, name, "", "", 0)
    }

    @Test
    fun `test that object constructed successfully`() {
        val actualName = pokemonEvolutionModel.name
        Assert.assertEquals(actualName, name)
    }
}