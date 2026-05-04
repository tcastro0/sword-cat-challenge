package com.tcastro.domain.favourites.usecase

import com.tcastro.domain.favourites.model.Favourite
import com.tcastro.domain.favourites.repository.FavouritesRepository


class SetFavouriteUseCase(private val repository: FavouritesRepository) {
    suspend operator fun invoke(favourite: Favourite) {
        if (repository.isFavourite(favourite.breedId)) {
            repository.removeFavourite(favourite.breedId)
        } else {
            repository.addFavourite(favourite)
        }
    }
}