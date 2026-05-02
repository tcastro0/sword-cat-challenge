package com.tcastro.feature.breeds.list.model


data class BreedUIModel(
    val id:String,
    val name: String,
    val imageUrl: String?,
    val isFavorite: Boolean
)