package com.tcastro.domain.breeds.usecase

import androidx.paging.PagingData
import com.tcastro.domain.breeds.model.Breed
import com.tcastro.domain.breeds.repository.BreedsRepository
import kotlinx.coroutines.flow.Flow

class GetBreedsUseCase(private val repository: BreedsRepository) {
    operator fun invoke(): Flow<PagingData<Breed>> = repository.getBreeds()
}