package com.tcastro.data.favourites.mapper

import com.tcastro.data.core.database.entity.FavouriteEntity
import com.tcastro.domain.favourites.model.Favourite


fun FavouriteEntity.toDomain() = Favourite(
    id = id,
    breedId = breedId,
    breedName = breedName,
    breedImageUrl = breedImageUrl,
    breedLifeSpan = breedLifeSpan,
    breedOrigin = breedOrigin
)

fun Favourite.toEntity() = FavouriteEntity(
    id = id,
    breedId = breedId,
    breedName = breedName,
    breedImageUrl = breedImageUrl,
    breedLifeSpan = breedLifeSpan,
    breedOrigin = breedOrigin
)