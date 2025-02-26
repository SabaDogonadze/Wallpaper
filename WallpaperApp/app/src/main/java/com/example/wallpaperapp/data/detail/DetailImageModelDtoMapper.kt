package com.example.wallpaperapp.data.detail

import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.domain.detail.DetailUnsplashURL

fun DetailImageModelDto.toDomain(): DetailImageModel {
    return DetailImageModel(
        id = this.id, width = this.width, height = this.height, urls = this.urls.toDomain()
    )
}

fun DetailUnsplashURLDto.toDomain(): DetailUnsplashURL {
    return DetailUnsplashURL(
        imageUrl = this.imageUrl
    )
}