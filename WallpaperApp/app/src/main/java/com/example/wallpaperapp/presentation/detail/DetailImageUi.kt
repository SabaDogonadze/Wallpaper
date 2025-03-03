package com.example.wallpaperapp.presentation.detail

data class DetailImageUi(
    val id: String,
    val width: Int,
    val height: Int,
    val urls: DetailUnsplashUiURL
)
data class DetailUnsplashUiURL(
    val imageUrl: String
)

