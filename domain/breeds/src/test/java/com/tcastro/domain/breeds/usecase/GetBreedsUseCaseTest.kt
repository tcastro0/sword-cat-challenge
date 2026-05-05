package com.tcastro.domain.breeds.usecase

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
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

    @Before
    fun setUp() {
        useCase = GetBreedsUseCase(repository)
    }

    @Test
    fun `invoke returns breeds from repository`() = runTest {
        val breeds = listOf(fakeBreed("1"), fakeBreed("2"), fakeBreed("3"))
        every { repository.getBreeds() } returns flowOf(PagingData.from(breeds))

        val result = useCase().asSnapshot()

        assertEquals(breeds, result)
    }

    @Test
    fun `invoke returns empty list when repository is empty`() = runTest {
        every { repository.getBreeds() } returns flowOf(PagingData.empty())

        val result = useCase().asSnapshot()

        assertEquals(emptyList<Breed>(), result)
    }



}