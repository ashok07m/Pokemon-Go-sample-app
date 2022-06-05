package com.ashok.pokemongo.ui.base

import androidx.lifecycle.Observer
import com.ashok.domain.ApiResult
import com.ashok.domain.interactor.GetPokemonDetailsUseCase
import com.ashok.domain.interactor.GetPokemonEvolutionChainUseCase
import com.ashok.pokemongo.R
import com.ashok.pokemongo.helpers.BaseTest
import com.ashok.pokemongo.helpers.TestData
import com.ashok.pokemongo.ui.details.DetailsViewModel
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class BaseViewModelTest : BaseTest() {

    private lateinit var viewModel: BaseViewModel

    private val getPokemonDetailsUseCase = mock<GetPokemonDetailsUseCase>()
    private val getPokemonEvolutionChainUseCase = mock<GetPokemonEvolutionChainUseCase>()

    @Mock
    lateinit var observer: Observer<ViewStateResult>

    override fun setup() {
        super.setup()
        MockitoAnnotations.openMocks(this)
        viewModel = DetailsViewModel(getPokemonDetailsUseCase, getPokemonEvolutionChainUseCase)
    }


    @Test
    fun `getApiResponse call returns success result`() = runTest {
        val pokemonId = 1
        whenever(getPokemonDetailsUseCase.invoke(pokemonId)).thenReturn(
            flowOf(ApiResult.Success(TestData.pokemonDetailModel))
        )
        val actual = viewModel.getApiResponse { getPokemonDetailsUseCase.invoke(pokemonId) }.first()
        assertTrue(actual is ApiResult.Success)
    }

    @Test
    fun `getApiResponse call returns Error result`() = runTest {
        val pokemonId = 1
        whenever(getPokemonDetailsUseCase.invoke(pokemonId)).thenReturn(
            flowOf(ApiResult.Error(TestData.otherException))
        )
        val actual = viewModel.getApiResponse { getPokemonDetailsUseCase.invoke(pokemonId) }.first()
        assertTrue(actual is ApiResult.Error)
    }

    @Test
    fun `getApiResponse updates loading state as true and false`() = runTest {
        val pokemonId = 1
        whenever(getPokemonDetailsUseCase.invoke(pokemonId)).thenReturn(
            flowOf(ApiResult.Error(TestData.otherException))
        )

        val viewStateResult = viewModel.viewLoadingStateResult
        viewStateResult.observeForever(observer)
        viewModel.getApiResponse { getPokemonDetailsUseCase.invoke(pokemonId) }
        verify(observer).onChanged(ViewStateResult.Loading(true))
        verify(observer).onChanged(ViewStateResult.Loading(false))
    }

    @Test
    fun `getNetworkErrorMessage return "No internet available" message`() = runTest {
        val actual = viewModel.getNetworkErrorMessage(TestData.networkException)
        assertTrue(actual == R.string.error_no_internet)
    }

    @Test
    fun `getNetworkErrorMessage return "Unable to fetch data from server" message`() = runTest {
        val actual = viewModel.getNetworkErrorMessage(TestData.otherException)
        assertTrue(actual == R.string.error_unable_to_load)
    }

}