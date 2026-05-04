package com.tcastro.data.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tcastro.data.core.database.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    fun getFavourites(): Flow<List<FavouriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(favourite: FavouriteEntity)

    @Query("DELETE FROM favourites WHERE breedId = :breedId")
    suspend fun removeFavourite(breedId: String)

    @Query("SELECT COUNT(*) FROM favourites WHERE breedId = :breedId")
    suspend fun isFavourite(breedId: String): Int
}