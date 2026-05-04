package com.tcastro.domain.favourites.usecase

import com.tcastro.domain.favourites.model.Favourite

fun fakeFavourite(
    id: Long = 1L,
    breedId: String = "abys",
    breedName: String = "Abyssinian",
    breedImageUrl: String? = "https://example.com/cat.jpg",
    breedLifeSpan: String = "14 - 15",
    breedOrigin: String = "Egypt"
) = Favourite(
    id = id,
    breedId = breedId,
    breedName = breedName,
    breedImageUrl = breedImageUrl,
    breedLifeSpan = breedLifeSpan,
    breedOrigin = breedOrigin
)