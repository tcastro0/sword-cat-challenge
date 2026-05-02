package com.tcastro.data.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val breedId: String,
    val prevPage: Int?,
    val nextPage: Int?
)