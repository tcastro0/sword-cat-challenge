package com.tcastro.data.breeds.mapper

import com.tcastro.data.breeds.remote.model.BreedDto
import com.tcastro.domain.breeds.model.Breed


fun BreedDto.toDomain(): Breed {
    return Breed(
        id = id,
        name = name,
        origin = origin.orEmpty(),
        temperament = temperament.orEmpty(),
        description = description.orEmpty(),
        imageUrl = image?.url,
    )
}