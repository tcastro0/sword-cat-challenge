package com.tcastro.feature.favourites.list.model


data class FavouriteUiModel(
    val breedId: String,
    val breedName: String,
    val breedImageUrl: String?,
    val breedLifeSpan: String,
    val breedOrigin: String
)