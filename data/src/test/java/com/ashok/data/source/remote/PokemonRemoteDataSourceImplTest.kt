package com.ashok.data.source.remote

import com.ashok.data.entity.response.evolutionchain.PokemonEvolutionChainEntity
import com.ashok.data.entity.response.pokemoncollection.PokemonEntity
import com.ashok.data.entity.response.pokemondetail.PokemonDetailEntity
import com.ashok.data.helpers.BaseTest
import com.ashok.data.helpers.TestData
import com.ashok.data.repository.PokemonRepositoryImpl
import com.ashok.data.source.api.PokemonApiService
import com.ashok.domain.ApiResult
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import retrofit2.Response

class PokemonRemoteDataSourceImplTest : BaseTest() {

    companion object {
        const val FAKE_POKEMON_ID = 1
    }

    private lateinit var pokemonRemoteDataSourceImpl: PokemonRemoteDataSourceImpl
    private val testDispatcher: TestDispatcher = mainCoroutineRule.testDispatcher

    @Mock
    lateinit var pokemonApiService: PokemonApiService

    @Mock
    lateinit var mockResponsePokemonEntity: Response<PokemonEntity>

    @Mock
    lateinit var mockResponsePokemonDetailEntity: Response<PokemonDetailEntity>

    @Mock
    lateinit var mockResponsePokemonEvolutionChainEntity: Response<PokemonEvolutionChainEntity>

    override fun setup() {
        super.setup()
        MockitoAnnotations.openMocks(this)
        pokemonRemoteDataSourceImpl =
            PokemonRemoteDataSourceImpl(pokemonApiService, testDispatcher, testDispatcher)
    }

    @Test
    fun `pokemonRemoteDataSourceImpl call getPokemonSpecies() atleast once`() = runTest {
        whenever(pokemonApiService.getPokemonSpecies()).thenReturn(
            Response.success(TestData.pokemonEntity)
        )
        pokemonRemoteDataSourceImpl.fetchPokemonCollection(
            PokemonApiService.STARTING_OFFSET_VALUE,
            PokemonApiService.PAGE_DEFAULT_SIZE
        )
        verify(pokemonApiService, times(1)).getPokemonSpecies()
        verifyNoMoreInteractions(pokemonApiService)
    }

    @Test
    fun `fetchPokemonCollection() returns success result`() = runTest {
        whenever(pokemonApiService.getPokemonSpecies()).thenReturn(
            Response.success(TestData.pokemonEntity)
        )
        val actual = pokemonRemoteDataSourceImpl.fetchPokemonCollection(
            PokemonApiService.STARTING_OFFSET_VALUE,
            PokemonApiService.PAGE_DEFAULT_SIZE
        )
        assertTrue(actual is ApiResult.Success)
        assertTrue((actual as ApiResult.Success).data.results?.first() == TestData.pokemonEntity.results?.first())
    }

    @Test
    fun `fetchPokemonCollection() returns Error result`() = runTest {
        whenever(pokemonApiService.getPokemonSpecies()).thenReturn(mockResponsePokemonEntity)
        val actual = pokemonRemoteDataSourceImpl.fetchPokemonCollection(
            PokemonApiService.STARTING_OFFSET_VALUE,
            PokemonApiService.PAGE_DEFAULT_SIZE
        )
        assertTrue(actual is ApiResult.Error)
    }

    @Test
    fun `fetchPokemonDetail() returns success result`() = runTest {
        whenever(pokemonApiService.getPokemonDetail(FAKE_POKEMON_ID)).thenReturn(
            Response.success(TestData.pokemonDetailEntity)
        )
        val actual = pokemonRemoteDataSourceImpl.fetchPokemonDetail(FAKE_POKEMON_ID)
        assertTrue(actual is ApiResult.Success)
        assertTrue((actual as ApiResult.Success).data.name == TestData.pokemonDetailEntity.name)
    }

    @Test
    fun `fetchPokemonDetail() returns Error result`() = runTest {
        whenever(pokemonApiService.getPokemonDetail(FAKE_POKEMON_ID)).thenReturn(
            mockResponsePokemonDetailEntity
        )
        val actual = pokemonRemoteDataSourceImpl.fetchPokemonDetail(FAKE_POKEMON_ID)
        assertTrue(actual is ApiResult.Error)
    }

    @Test
    fun `fetchPokemonEvolutionChain() returns success result`() = runTest {
        whenever(pokemonApiService.getEvolutionChain(FAKE_POKEMON_ID)).thenReturn(
            Response.success(TestData.pokemonEvolutionChainEntity)
        )
        val actual = pokemonRemoteDataSourceImpl.fetchPokemonEvolutionChain(FAKE_POKEMON_ID)
        assertTrue(actual is ApiResult.Success)
    }

    @Test
    fun `fetchPokemonEvolutionChain() returns Error result`() = runTest {
        whenever(pokemonApiService.getEvolutionChain(FAKE_POKEMON_ID)).thenReturn(
            mockResponsePokemonEvolutionChainEntity
        )
        val actual = pokemonRemoteDataSourceImpl.fetchPokemonEvolutionChain(FAKE_POKEMON_ID)
        assertTrue(actual is ApiResult.Error)
    }
}