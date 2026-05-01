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

class GetBreedDetailUseCaseTest {

    private val repository: BreedsRepository = mockk()
    private lateinit var useCase: GetBreedDetailUseCase
    val breeds = listOf(fakeBreed("1"), fakeBreed("2"), fakeBreed("3"))


    @Before
    fun setUp() {
        useCase = GetBreedDetailUseCase(repository)
    }

    @Test
    fun `invoke returns breed when found`() = runTest {
        val breed = fakeBreed("1")
        coEvery { repository.getBreedById(breed.id) } returns flowOf(breeds.first { it.id == breed.id })

        useCase("1").test {
            val result = awaitItem()
            assertEquals(breed, result)
            awaitComplete()
        }

    }

    @Test
    fun `invoke returns null when none is found`() = runTest {
        coEvery { repository.getBreedById("unknown") } returns flowOf(null)

        useCase("unknown").test {
            val result = awaitItem()
            assertEquals(null, result)
            awaitComplete()
        }
    }

}