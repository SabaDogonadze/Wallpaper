package com.example.wallpaperapp.domain.detail

data class DetailImageModel(
    val id: String,
    val width: Int,
    val height: Int,
    val urls: DetailUnsplashURL
)

data class DetailUnsplashURL(
    val imageUrl: String
)
