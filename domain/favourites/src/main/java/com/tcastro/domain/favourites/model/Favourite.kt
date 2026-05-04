package com.tcastro.domain.favourites.model

data class Favourite(
    val id: Long = 0,
    val breedId: String,
    val breedName: String,
    val breedImageUrl: String?,
    val breedLifeSpan: String,
    val breedOrigin: String
)