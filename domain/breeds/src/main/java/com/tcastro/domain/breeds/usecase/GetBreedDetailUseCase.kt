package com.tcastro.domain.breeds.usecase

import com.tcastro.domain.breeds.model.Breed
import com.tcastro.domain.breeds.repository.BreedsRepository
import kotlinx.coroutines.flow.Flow


class GetBreedDetailUseCase(private val repository: BreedsRepository) {
    operator fun invoke(breedId: String): Flow<Breed?> =
        repository.getBreedById(breedId)
}