package com.example.wallpaperapp.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.wallpaperapp.local.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favouriteentity ")
    fun getAll(): Flow<List<FavouriteEntity>>

    @Insert
    suspend fun insertImage(image: FavouriteEntity)

    @Delete
    suspend fun delete(image: FavouriteEntity)
}