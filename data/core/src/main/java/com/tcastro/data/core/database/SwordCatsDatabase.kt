package com.tcastro.data.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tcastro.data.core.database.dao.BreedDao
import com.tcastro.data.core.database.dao.RemoteKeyDao
import com.tcastro.data.core.database.entity.BreedEntity
import com.tcastro.data.core.database.entity.RemoteKeyEntity


@Database(
    entities = [BreedEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SwordCatsDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}