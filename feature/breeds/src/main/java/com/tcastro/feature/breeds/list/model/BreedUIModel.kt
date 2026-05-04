package com.tcastro.feature.breeds.list.model


data class BreedUIModel(
    val id:String,
    val name: String,
    val imageUrl: String?,
    val lifespan: String?=null,
    val origin: String?=null,
    val isFavorite: Boolean
)