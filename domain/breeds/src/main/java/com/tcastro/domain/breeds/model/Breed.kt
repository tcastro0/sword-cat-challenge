package com.tcastro.domain.breeds.model

data class Breed(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val imageUrl: String?,
)