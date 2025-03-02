package com.example.wallpaperapp.data.local.favourite

import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.domain.favourite.LocalFavouriteRepository
import com.example.wallpaperapp.data.local.dao.FavouriteDao
import com.example.wallpaperapp.data.local.mapper.toData
import com.example.wallpaperapp.data.local.mapper.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalFavouriteRepositoryImpl @Inject constructor(private val favouriteDao: FavouriteDao) :
    LocalFavouriteRepository {
    override  fun getAll(): Flow<List<DetailImageModel>> {
        return favouriteDao.getAll().map { favourite ->
            favourite.map {
                it.toDomain()
            }
        }
    }
    override suspend fun insert(image: DetailImageModel) {
        favouriteDao.insertImage(image.toData())
    }

    override suspend fun delete(image: DetailImageModel) {
        favouriteDao.delete(image.toData())
    }
}