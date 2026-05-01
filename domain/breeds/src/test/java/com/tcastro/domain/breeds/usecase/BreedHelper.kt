package com.tcastro.domain.breeds.usecase

import com.tcastro.domain.breeds.model.Breed


fun fakeBreed(
    id: String = "fake",
    name: String = "$id breed",
    origin: String = "$id origin",
    temperament: String = "Active, Energetic",
    description: String = "$id description",
    imageUrl: String? = "https://example.com/cat.jpg",
) = Breed(
    id = id,
    name = name,
    origin = origin,
    temperament = temperament,
    description = description,
    imageUrl = imageUrl,
)