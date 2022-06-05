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

class GetPokemonDetailsUseCaseTest : BaseTest() {

    private lateinit var getPokemonDetailsUseCase: GetPokemonDetailsUseCase

    private val pokemonId = 1

    @Mock
    lateinit var pokemonRepository: PokemonRepository

    override fun setup() {
        super.setup()
        MockitoAnnotations.openMocks(this)
        getPokemonDetailsUseCase = GetPokemonDetailsUseCase(pokemonRepository)
    }

    @Test
    fun `verify the repository's fetchPokemonDetail() invoked`() = runTest {
        getPokemonDetailsUseCase.invoke(pokemonId)
        verify(pokemonRepository, times(1)).fetchPokemonDetail(pokemonId)
    }

    @Test
    fun `verify the repository's fetchPokemonDetail() emits a non null object`() = runTest {
        whenever(pokemonRepository.fetchPokemonDetail(pokemonId)).thenReturn(
            flowOf(
                ApiResult.Success(
                    TestData.pokemonDetailModel
                )
            )
        )
        val actual = getPokemonDetailsUseCase.invoke(pokemonId).first()
        Assert.assertNotNull(actual)
    }
}