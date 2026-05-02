package com.tcastro.data.breeds.mapper

import com.tcastro.data.breeds.remote.model.BreedDto
import com.tcastro.data.core.database.entity.BreedEntity
import com.tcastro.domain.breeds.model.Breed


fun BreedDto.toDomain(): Breed {
    return Breed(
        id = id,
        name = name,
        origin = origin.orEmpty(),
        temperament = temperament.orEmpty(),
        description = description.orEmpty(),
        lifeSpan = lifeSpan.orEmpty(),
        imageUrl = image?.url,
        imageId = image?.id
    )
}

fun BreedEntity.toDomain(): Breed = Breed(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    lifeSpan = lifeSpan,
    imageUrl = imageUrl,
    imageId =  referenceImageId
)

fun BreedDto.toEntity(): BreedEntity = BreedEntity(
    id = id,
    name = name,
    origin = origin.orEmpty(),
    temperament = temperament.orEmpty(),
    description = description.orEmpty(),
    lifeSpan = lifeSpan.orEmpty(),
    imageUrl = image?.url,
    referenceImageId = image?.id
)

fun Breed.toEntity(): BreedEntity = BreedEntity(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    lifeSpan = lifeSpan,
    imageUrl = imageUrl,
    referenceImageId = imageId
)
