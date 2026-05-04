package com.tcastro.domain.favourites.usecase

import com.tcastro.domain.favourites.repository.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetFavouriteIdsUseCase(private val repository: FavouritesRepository) {
    operator fun invoke(): Flow<Set<String>> =
        repository.getFavourites().map { favourites ->
            favourites.map { it.breedId }.toSet()
        }
}