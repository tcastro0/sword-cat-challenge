package com.tcastro.domain.breeds.repository

import androidx.paging.PagingData
import com.tcastro.domain.breeds.model.Breed
import kotlinx.coroutines.flow.Flow

interface BreedsRepository {
    fun getBreeds(): Flow<PagingData<Breed>>
    fun searchBreeds(query: String): Flow<List<Breed>>
    suspend fun getBreedById(id: String): Flow<Breed?>
}