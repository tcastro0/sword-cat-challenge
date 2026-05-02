package com.tcastro.data.breeds.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BreedDto(
    val id: String,
    val name: String,
    val origin: String?,
    val temperament: String?,
    val description: String?,
    val lifeSpan: String?,
    val image: BreedImageDto?
)

@JsonClass(generateAdapter = true)
data class BreedImageDto(
    val id: String?,
    val url: String?
)