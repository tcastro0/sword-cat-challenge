package com.tcastro.data.favourites.repository

import com.tcastro.data.core.database.FavouriteDao
import com.tcastro.data.favourites.mapper.toDomain
import com.tcastro.data.favourites.mapper.toEntity
import com.tcastro.domain.favourites.model.Favourite
import com.tcastro.domain.favourites.repository.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class FavouritesRepositoryImpl(
    private val favouriteDao: FavouriteDao
) : FavouritesRepository {

    override fun getFavourites(): Flow<List<Favourite>> =
        favouriteDao.getFavourites().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun addFavourite(favourite: Favourite) {
        favouriteDao.addFavourite(favourite.toEntity())
    }

    override suspend fun removeFavourite(breedId: String) {
        favouriteDao.removeFavourite(breedId)
    }

    override suspend fun isFavourite(breedId: String): Boolean {
        return favouriteDao.isFavourite(breedId) > 0
    }
}