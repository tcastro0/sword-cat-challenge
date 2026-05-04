package com.tcastro.feature.breeds.detail.mapper

import com.tcastro.domain.breeds.model.Breed
import com.tcastro.feature.breeds.detail.model.BreedDetailUiModel


fun Breed.toDetailUiModel() = BreedDetailUiModel(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    lifespan = lifeSpan,
    imageUrl = imageUrl,
    isFavourite = isFavourite
)