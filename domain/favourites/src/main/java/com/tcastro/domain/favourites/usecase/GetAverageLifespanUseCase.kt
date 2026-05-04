package com.tcastro.domain.favourites.usecase

import com.tcastro.domain.favourites.repository.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetAverageLifespanUseCase(private val repository: FavouritesRepository) {
    operator fun invoke(): Flow<Double> = repository.getFavourites().map { favourites ->
        if (favourites.isEmpty()) return@map 0.0
        favourites.mapNotNull { favourite ->
            favourite.breedLifeSpan
                .split("-")
                .firstOrNull()
                ?.trim()
                ?.toDoubleOrNull()
        }.average()
    }
}