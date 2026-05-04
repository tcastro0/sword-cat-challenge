package com.tcastro.domain.favourites.usecase


import com.tcastro.domain.favourites.repository.FavouritesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetFavouriteUseCaseTest {

    private val repository: FavouritesRepository = mockk(relaxed = true)
    private lateinit var useCase: SetFavouriteUseCase

    @Before
    fun setUp() {
        useCase = SetFavouriteUseCase(repository)
    }

    @Test
    fun `invoke adds favourite when not already favourite`() = runTest {
        val favourite = fakeFavourite()
        coEvery { repository.isFavourite(favourite.breedId) } returns false

        useCase(favourite)

        coVerify { repository.addFavourite(favourite) }
        coVerify(exactly = 0) { repository.removeFavourite(any()) }
    }

    @Test
    fun `invoke removes favourite when already favourite`() = runTest {
        val favourite = fakeFavourite()
        coEvery { repository.isFavourite(favourite.breedId) } returns true

        useCase(favourite)

        coVerify { repository.removeFavourite(favourite.breedId) }
        coVerify(exactly = 0) { repository.addFavourite(any()) }
    }
}