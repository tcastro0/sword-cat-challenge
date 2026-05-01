package com.tcastro.domain.breeds.usecase

import app.cash.turbine.test
import com.tcastro.domain.breeds.model.Breed
import com.tcastro.domain.breeds.repository.BreedsRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetBreedsUseCaseTest {

    private val repository: BreedsRepository = mockk()
    private lateinit var useCase: GetBreedsUseCase
    val breeds = listOf(fakeBreed("1"), fakeBreed("2"), fakeBreed("3"))


    @Before
    fun setUp() {
        useCase = GetBreedsUseCase(repository)
    }

    @Test
    fun `fetch returns breeds from repository`() = runTest {

        every { repository.getBreeds() } returns flowOf(breeds)

        useCase().test {
            val result = awaitItem()
            assertEquals(breeds, result)
            awaitComplete()
        }

    }

    @Test
    fun `fetch result is empty`() = runTest {
        every { repository.getBreeds() } returns flowOf(emptyList())

        useCase().test {
            val result = awaitItem()
            assertEquals(emptyList<Breed>(), result)
            awaitComplete()
        }

    }


}