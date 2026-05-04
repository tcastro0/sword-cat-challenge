package com.tcastro.domain.favourites.usecase

import app.cash.turbine.test
import com.tcastro.domain.favourites.model.Favourite
import com.tcastro.domain.favourites.repository.FavouritesRepository
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetFavouritesUseCaseTest {

    private val repository: FavouritesRepository = mockk()
    private lateinit var useCase: GetFavouritesUseCase

    @Before
    fun setUp() {
        useCase = GetFavouritesUseCase(repository)
    }

    @Test
    fun `invoke returns favourites from repository`() = runTest {
        val favourites = listOf(fakeFavourite(1), fakeFavourite(2))
        every { repository.getFavourites() } returns flowOf(favourites)

        useCase().test {
            assertEquals(favourites, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns empty list when no favourites`() = runTest {
        every { repository.getFavourites() } returns flowOf(emptyList())

        useCase().test {
            assertEquals(emptyList<Favourite>(), awaitItem())
            awaitComplete()
        }
    }


}