package com.tcastro.domain.breeds.usecase

import com.tcastro.domain.breeds.model.Breed
import com.tcastro.domain.breeds.repository.BreedsRepository
import kotlinx.coroutines.flow.Flow


class SearchBreedUseCase(private val repository: BreedsRepository) {
    operator fun invoke(query: String): Flow<List<Breed>> =
        repository.searchBreeds(query)
}