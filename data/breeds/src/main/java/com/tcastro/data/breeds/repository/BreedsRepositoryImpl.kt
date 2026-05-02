package com.tcastro.data.breeds.repository

import com.tcastro.data.breeds.mapper.toDomain
import com.tcastro.data.breeds.remote.service.BreedsService
import com.tcastro.domain.breeds.model.Breed
import com.tcastro.domain.breeds.repository.BreedsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class BreedsRepositoryImpl(
    private val service: BreedsService
) : BreedsRepository {

    override fun getBreeds(): Flow<List<Breed>> = flow {
        val breeds = service.getBreeds().map { it.toDomain() }
        emit(breeds)
    }

    override fun searchBreeds(query: String): Flow<List<Breed>> = flow {
        val breeds = service.searchBreeds(query).map { it.toDomain() }
        emit(breeds)
    }

    override suspend fun getBreedById(id: String): Flow<Breed?> = flow {
        val breed = service.getBreedById(id).toDomain()
        emit(breed)
    }
}