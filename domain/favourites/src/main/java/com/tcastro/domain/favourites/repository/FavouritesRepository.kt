package com.tcastro.domain.favourites.repository

import com.tcastro.domain.favourites.model.Favourite
import kotlinx.coroutines.flow.Flow


interface FavouritesRepository {
    fun getFavourites(): Flow<List<Favourite>>
    suspend fun addFavourite(favourite: Favourite)
    suspend fun removeFavourite(breedId: String)
    suspend fun isFavourite(breedId: String): Boolean
}