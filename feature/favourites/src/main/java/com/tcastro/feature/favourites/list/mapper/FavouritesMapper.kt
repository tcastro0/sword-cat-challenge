package com.tcastro.feature.favourites.list.mapper

import com.tcastro.domain.favourites.model.Favourite
import com.tcastro.feature.favourites.list.model.FavouriteUiModel


fun Favourite.toUiModel() = FavouriteUiModel(
    breedId = breedId,
    breedName = breedName,
    breedImageUrl = breedImageUrl,
    breedLifeSpan = breedLifeSpan,
    breedOrigin = breedOrigin
)

fun FavouriteUiModel.toDomain() = Favourite(
    breedId = breedId,
    breedName = breedName,
    breedImageUrl = breedImageUrl,
    breedLifeSpan = breedLifeSpan,
    breedOrigin = breedOrigin
)