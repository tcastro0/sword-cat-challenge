package com.tcastro.feature.breeds.list.mapper

import com.tcastro.domain.breeds.model.Breed
import com.tcastro.feature.breeds.list.model.BreedUIModel


fun Breed.toUIModel(): BreedUIModel{
    return BreedUIModel(
            id = this.id,
            name = this.name,
            imageUrl = this.imageUrl,
            isFavorite = this.isFavourite
        )
}
fun List<Breed>.toUIModel(): List<BreedUIModel>{
    return this.map {
        BreedUIModel(
            id = it.id,
            name = it.name,
            imageUrl = it.imageUrl,
            isFavorite = it.isFavourite
        )
    }
}