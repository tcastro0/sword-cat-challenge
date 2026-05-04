package com.tcastro.data.core.database.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val breedId: String,
    val breedName: String,
    val breedImageUrl: String?,
    val breedLifeSpan: String,
    val breedOrigin: String
)