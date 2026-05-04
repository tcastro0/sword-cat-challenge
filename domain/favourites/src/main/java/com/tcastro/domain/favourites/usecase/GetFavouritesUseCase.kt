package com.tcastro.domain.favourites.usecase

import com.tcastro.domain.favourites.model.Favourite
import com.tcastro.domain.favourites.repository.FavouritesRepository
import kotlinx.coroutines.flow.Flow


class GetFavouritesUseCase(private val repository: FavouritesRepository) {
    operator fun invoke(): Flow<List<Favourite>> = repository.getFavourites()
}