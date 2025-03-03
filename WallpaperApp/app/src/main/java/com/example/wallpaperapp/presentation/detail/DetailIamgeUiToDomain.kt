package com.example.wallpaperapp.presentation.detail

import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.domain.detail.DetailUnsplashURL

fun DetailImageUi.toDomain(): DetailImageModel {
    return DetailImageModel(
        id = this.id,
        width = this.width,
        height = this.height,
        urls = this.urls.toDomain()
    )
}

fun DetailUnsplashUiURL.toDomain(): DetailUnsplashURL {
    return DetailUnsplashURL(
        imageUrl = this.imageUrl
    )
}
