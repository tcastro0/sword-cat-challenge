package com.tcastro.data.core.database.dao


import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tcastro.data.core.database.entity.BreedEntity

@Dao
interface BreedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(breeds: List<BreedEntity>)

    @Query("SELECT * FROM breeds")
    fun getBreedsPagingSource(): PagingSource<Int, BreedEntity>

    @Query("SELECT * FROM breeds WHERE id = :breedId")
    suspend fun getBreedById(breedId: String): BreedEntity?

    @Query("DELETE FROM breeds")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM breeds")
    suspend fun getCount():Int
}