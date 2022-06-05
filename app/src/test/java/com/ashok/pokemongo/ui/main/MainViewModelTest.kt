package com.ashok.pokemongo.ui.main

import androidx.paging.PagingData
import com.ashok.domain.interactor.GetPokemonListUseCase
import com.ashok.pokemongo.helpers.BaseTest
import com.ashok.pokemongo.helpers.TestData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MainViewModelTest : BaseTest() {

    private val getPokemonListUseCase = mock<GetPokemonListUseCase>()

    private lateinit var viewModel: MainViewModel

    override fun setup() {
        super.setup()
        viewModel = MainViewModel(getPokemonListUseCase)
    }

    @Test
    fun `getPokemonListUseCase call invoke() at least once`() = runTest {
        whenever(getPokemonListUseCase.invoke()).thenReturn(flowOf(PagingData.empty()))
        viewModel.loadPokemonList()
        verify(getPokemonListUseCase, times(1)).invoke()
    }

    @Test
    fun `pagingDataFlow get initialised when loadPokemonList() invoked`() = runTest {
        val data = TestData.pokemonModel
        val expectedPage = PagingData.from(listOf(data))
        val testData = flow {
            emit(expectedPage)
        }
        whenever(getPokemonListUseCase.invoke()).thenReturn(testData)
        viewModel.loadPokemonList()
        val result = viewModel.pagingDataFlow.first()
        Assert.assertNotNull(result)
    }
}