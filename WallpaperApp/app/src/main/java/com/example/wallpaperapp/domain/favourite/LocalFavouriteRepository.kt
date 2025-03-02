package com.example.wallpaperapp.domain.favourite

import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.data.local.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

interface LocalFavouriteRepository {
    fun getAll(): Flow<List<DetailImageModel>>
    suspend fun insert(image: DetailImageModel)
    suspend fun delete(image: DetailImageModel)
}