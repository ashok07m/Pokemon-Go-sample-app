package com.ashok.domain.interactor

import androidx.paging.PagingData
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

class GetPokemonListUseCaseTest : BaseTest() {

    private lateinit var getPokemonListUseCase: GetPokemonListUseCase

    @Mock
    lateinit var pokemonRepository: PokemonRepository

    override fun setup() {
        super.setup()
        MockitoAnnotations.openMocks(this)
        getPokemonListUseCase = GetPokemonListUseCase(pokemonRepository)
    }

    @Test
    fun `verify the repository's loadPokemonList() invoked`() = runTest {
        getPokemonListUseCase.invoke()
        verify(pokemonRepository, times(1)).loadPokemonList()
    }

    @Test
    fun `verify the repository's loadPokemonList() emits a non null object`() = runTest {
        whenever(pokemonRepository.loadPokemonList()).thenReturn(
            flowOf(
                PagingData.from(
                    listOf(
                        TestData.pokemonModel
                    )
                )
            )
        )
        val actual = getPokemonListUseCase.invoke().first()
        Assert.assertNotNull(actual)
    }
}