package com.ashok.data.repository

import com.ashok.data.entity.mapper.toPokemonDetailModel
import com.ashok.data.helpers.BaseTest
import com.ashok.data.helpers.TestData
import com.ashok.data.source.remote.PokemonRemoteDataSourceImpl
import com.ashok.domain.ApiResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class PokemonRepositoryImplTest : BaseTest() {

    companion object {
        const val FAKE_POKEMON_ID = 1
    }

    private lateinit var pokemonRepositoryImpl: PokemonRepositoryImpl
    private val testDispatcher: TestDispatcher = mainCoroutineRule.testDispatcher

    @Mock
    lateinit var remoteDataSource: PokemonRemoteDataSourceImpl


    override fun setup() {
        super.setup()
        MockitoAnnotations.openMocks(this)
        pokemonRepositoryImpl = PokemonRepositoryImpl(remoteDataSource, testDispatcher)
    }

    @Test
    fun `pokemonRepositoryImpl call fetchPokemonDetail() atleast once`() = runTest {
        whenever(remoteDataSource.fetchPokemonDetail(FAKE_POKEMON_ID)).thenReturn(
            ApiResult.Success(TestData.pokemonDetailEntity)
        )
        pokemonRepositoryImpl.fetchPokemonDetail(FAKE_POKEMON_ID)
        verify(remoteDataSource, times(1)).fetchPokemonDetail(FAKE_POKEMON_ID)
        verifyNoMoreInteractions(remoteDataSource)
    }

    @Test
    fun `pokemonRepositoryImpl call to fetchPokemonDetail returns success flow`() = runTest {
        whenever(remoteDataSource.fetchPokemonDetail(FAKE_POKEMON_ID)).thenReturn(
            ApiResult.Success(TestData.pokemonDetailEntity)
        )
        val actualFlow = pokemonRepositoryImpl.fetchPokemonDetail(FAKE_POKEMON_ID).first()
        Assert.assertTrue(actualFlow is ApiResult.Success<*>)
    }

    @Test
    fun `pokemonRepositoryImpl call to fetchPokemonDetail returns Error flow`() = runTest {
        whenever(remoteDataSource.fetchPokemonDetail(FAKE_POKEMON_ID)).thenReturn(
            ApiResult.Error(TestData.otherException)
        )
        val actualFlow = pokemonRepositoryImpl.fetchPokemonDetail(FAKE_POKEMON_ID).first()
        Assert.assertTrue(actualFlow is ApiResult.Error)
    }


    @Test
    fun `pokemonRepositoryImpl call to fetchPokemonEvolutionChain returns success flow`() = runTest {
        whenever(remoteDataSource.fetchPokemonEvolutionChain(FAKE_POKEMON_ID)).thenReturn(
            ApiResult.Success(TestData.pokemonEvolutionChainEntity)
        )
        val actualFlow = pokemonRepositoryImpl.fetchPokemonEvolutionChain(FAKE_POKEMON_ID).first()

        Assert.assertTrue(actualFlow is ApiResult.Success<*>)
    }

    @Test
    fun `pokemonRepositoryImpl call to fetchPokemonEvolutionChain returns Error flow`() = runTest {
        whenever(remoteDataSource.fetchPokemonEvolutionChain(FAKE_POKEMON_ID)).thenReturn(
            ApiResult.Error(TestData.otherException)
        )
        val actualFlow = pokemonRepositoryImpl.fetchPokemonEvolutionChain(FAKE_POKEMON_ID).first()
        Assert.assertTrue(actualFlow is ApiResult.Error)
    }

    @Test
    fun `pokemonRepositoryImpl call to transformObject returns transformed object`() = runTest {
        val actual = pokemonRepositoryImpl.transformObject { TestData.pokemonDetailEntity.toPokemonDetailModel()  }
        Assert.assertEquals(actual.name, TestData.pokemonDetailEntity.name)
    }
}