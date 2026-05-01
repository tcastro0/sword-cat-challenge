package com.tcastro.domain.breeds.repository

import com.tcastro.domain.breeds.model.Breed
import kotlinx.coroutines.flow.Flow

interface BreedsRepository {
    fun getBreeds(): Flow<List<Breed>>
    fun searchBreeds(query: String): Flow<List<Breed>>
    fun getBreedById(id: String): Flow<Breed?>
}