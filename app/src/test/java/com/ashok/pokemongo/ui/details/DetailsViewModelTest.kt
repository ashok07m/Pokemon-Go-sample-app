package com.ashok.pokemongo.ui.details

import com.ashok.domain.ApiResult
import com.ashok.domain.interactor.GetPokemonDetailsUseCase
import com.ashok.domain.interactor.GetPokemonEvolutionChainUseCase
import com.ashok.pokemongo.R
import com.ashok.pokemongo.helpers.BaseTest
import com.ashok.pokemongo.helpers.TestData
import com.ashok.pokemongo.helpers.getOrAwaitValue
import com.ashok.pokemongo.ui.base.ViewStateResult
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DetailsViewModelTest : BaseTest() {

    private lateinit var viewModel: DetailsViewModel

    private val getPokemonDetailsUseCase = mock<GetPokemonDetailsUseCase>()
    private val getPokemonEvolutionChainUseCase = mock<GetPokemonEvolutionChainUseCase>()

    override fun setup() {
        super.setup()
        viewModel = DetailsViewModel(getPokemonDetailsUseCase, getPokemonEvolutionChainUseCase)
    }

    @Test
    fun `getPokemonDetailsUseCase call invoke() at least once`() = runTest {
        val pokemonId = 1
        whenever(getPokemonDetailsUseCase.invoke(pokemonId)).thenReturn(
            flowOf(ApiResult.Success(TestData.pokemonDetailModel))
        )
        getPokemonDetailsUseCase.invoke(pokemonId)
        verify(getPokemonDetailsUseCase, times(1)).invoke(pokemonId)
    }

    @Test
    fun `getPokemonEvolutionChainUseCase call invoke() at least once`() = runTest {
        val pokemonId = 1
        whenever(getPokemonEvolutionChainUseCase.invoke(pokemonId)).thenReturn(
            flowOf(ApiResult.Success(TestData.pokemonEvolutionModel))
        )
        getPokemonEvolutionChainUseCase.invoke(pokemonId)
        verify(getPokemonEvolutionChainUseCase, times(1)).invoke(pokemonId)
    }

    @Test
    fun `pokemonDetails livedata sets with success result`() = runTest {
        val pokemonId = 1
        whenever(getPokemonDetailsUseCase.invoke(pokemonId)).thenReturn(
            flowOf(ApiResult.Success(TestData.pokemonDetailModel))
        )
        viewModel.getPokemonDetails(pokemonId)
        runCurrent()
        val value = viewModel.pokemonDetails.getOrAwaitValue()
        assertTrue(value is ViewStateResult.Success<*>)
    }

    @Test
    fun `pokemonDetails livedata sets with Error result`() = runTest {
        val pokemonId = 1
        whenever(getPokemonDetailsUseCase.invoke(pokemonId)).thenReturn(
            flowOf(ApiResult.Error(TestData.networkException))
        )
        viewModel.getPokemonDetails(pokemonId)
        runCurrent()
        val value = viewModel.pokemonDetails.getOrAwaitValue()
        assertTrue(value is ViewStateResult.Error)
    }

    @Test
    fun `evolutionChainInfo livedata sets with success result`() = runTest {
        val pokemonId = 1
        whenever(getPokemonEvolutionChainUseCase.invoke(pokemonId)).thenReturn(
            flowOf(ApiResult.Success(TestData.pokemonEvolutionModel))
        )
        viewModel.getPokemonEvolutionInfo(pokemonId)
        runCurrent()
        val value = viewModel.evolutionChainInfo.getOrAwaitValue()
        assertTrue(value is ViewStateResult.Success<*>)
    }

    @Test
    fun `evolutionChainInfo livedata sets with Error result`() = runTest {
        val pokemonId = 1
        whenever(getPokemonEvolutionChainUseCase.invoke(pokemonId)).thenReturn(
            flowOf(ApiResult.Error(TestData.networkException))
        )
        viewModel.getPokemonEvolutionInfo(pokemonId)
        runCurrent()
        val value = viewModel.evolutionChainInfo.getOrAwaitValue()
        assertTrue(value is ViewStateResult.Error)
    }

    @Test
    fun `capture rate difference shows value in green color`() {
        val captureRate1 = 20
        val captureRate2 = 10
        val expected = Pair(captureRate1 - captureRate2, R.color.green)
        val actual = viewModel.getCaptureDifference(captureRate1, captureRate2)
        assertTrue(actual.first == expected.first)
        assertTrue(actual.second == expected.second)
    }

    @Test
    fun `capture rate difference shows value in red color`() {
        val captureRate1 = 5
        val captureRate2 = 10
        val expected = Pair(captureRate1 - captureRate2, R.color.red)
        val actual = viewModel.getCaptureDifference(captureRate1, captureRate2)
        assertTrue(actual.first == expected.first)
        assertTrue(actual.second == expected.second)
    }

}