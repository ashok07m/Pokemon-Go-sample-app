package com.ashok.domain.interactor

import com.ashok.domain.ApiResult
import com.ashok.domain.helpers.BaseTest
import com.ashok.domain.helpers.TestData
import com.ashok.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetPokemonEvolutionChainUseCaseTest : BaseTest() {

    private lateinit var getPokemonEvolutionChainUseCase: GetPokemonEvolutionChainUseCase

    @Mock
    lateinit var pokemonRepository: PokemonRepository

    private val pokemonId = 1

    override fun setup() {
        super.setup()
        MockitoAnnotations.openMocks(this)
        getPokemonEvolutionChainUseCase = GetPokemonEvolutionChainUseCase(pokemonRepository)
    }

    @Test
    fun `verify the repository's fetchPokemonEvolutionChain() invoked`() = runTest {
        getPokemonEvolutionChainUseCase.invoke(pokemonId)
        verify(pokemonRepository, times(1)).fetchPokemonEvolutionChain(pokemonId)
    }

    @Test
    fun `verify the repository's fetchPokemonEvolutionChain() emits a non null object`() = runTest {
        whenever(pokemonRepository.fetchPokemonEvolutionChain(pokemonId)).thenReturn(
            flowOf(
                ApiResult.Success(TestData.pokemonEvolutionModel)
            )
        )
        val actual = getPokemonEvolutionChainUseCase.invoke(pokemonId).first()
        Assert.assertNotNull(actual)
    }
}