package com.tcastro.domain.favourites.usecase

import app.cash.turbine.test
import com.tcastro.domain.favourites.repository.FavouritesRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetAverageLifespanUseCaseTest {

    private val repository: FavouritesRepository = mockk()
    private lateinit var useCase: GetAverageLifespanUseCase

    @Before
    fun setUp() {
        useCase = GetAverageLifespanUseCase(repository)
    }

    @Test
    fun `invoke returns correct average lifespan`() = runTest {
        val favourites = listOf(
            fakeFavourite(breedLifeSpan = "12 - 15"),
            fakeFavourite(breedLifeSpan = "14 - 17"),
            fakeFavourite(breedLifeSpan = "10 - 12")
        )
        every { repository.getFavourites() } returns flowOf(favourites)

        useCase().test {
            assertEquals(12.0, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns zero when no favourites`() = runTest {
        every { repository.getFavourites() } returns flowOf(emptyList())

        useCase().test {
            assertEquals(0.0, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke ignores breeds with unparseable lifespan`() = runTest {
        val favourites = listOf(
            fakeFavourite(breedLifeSpan = "12 - 15"),
            fakeFavourite(breedLifeSpan = "unknown"),
            fakeFavourite(breedLifeSpan = "14 - 17")
        )
        every { repository.getFavourites() } returns flowOf(favourites)

        useCase().test {
            assertEquals(13.0, awaitItem())
            awaitComplete()
        }
    }
}