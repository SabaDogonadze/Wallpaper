package com.example.wallpaperapp.local.mapper

import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.domain.detail.DetailUnsplashURL
import com.example.wallpaperapp.local.entity.FavouriteEntity

fun FavouriteEntity.toDomain():DetailImageModel {
    return DetailImageModel(
        id = this.id, width = 0, height = 0, urls = DetailUnsplashURL(imageUrl = this.imageUrl)
    )
}

fun DetailImageModel.toData():FavouriteEntity{
    return FavouriteEntity(
        id =this.id, imageUrl = this.urls.imageUrl
    )
}