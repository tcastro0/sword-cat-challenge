package com.tcastro.domain.breeds.usecase

import app.cash.turbine.test
import com.tcastro.domain.breeds.repository.BreedsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class SearchBreedUseCaseTest {

    private val repository: BreedsRepository = mockk()
    private lateinit var useCase: SearchBreedUseCase

    @Before
    fun setUp() {
        useCase = SearchBreedUseCase(repository)
    }

    @Test
    fun `search returns matching breeds for query`() = runTest {
        val query = "1"
        coEvery { repository.searchBreeds(query) } returns flowOf(listOf(fakeBreed("1")))

        useCase(query).test {
              val result = awaitItem()
              assertEquals(result.isNotEmpty(),true)
              assertEquals(result.first().id,"1")
              awaitComplete()
          }

    }

    @Test
    fun `search returns empty list when no breeds match`() = runTest {
        val query = "asd"
        coEvery { repository.searchBreeds(query) } returns flowOf(emptyList())

        useCase(query).test {
            val result = awaitItem()
            assertEquals(result.isEmpty(),true)
            awaitComplete()
        }

    }

}