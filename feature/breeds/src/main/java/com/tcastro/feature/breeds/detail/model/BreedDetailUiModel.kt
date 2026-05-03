package com.tcastro.feature.breeds.detail.model



data class BreedDetailUiModel(
    val id:String,
    val name: String,
    val imageUrl: String?,
    val lifespan: String?=null,
    val isFavourite: Boolean = false,
    val temperament: String,
    val description: String,
    val origin: String,
)