package com.example.wallpaperapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.wallpaperapp.data.local.entity.FavouriteEntity
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