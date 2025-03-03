package com.example.wallpaperapp.presentation.discovery

data class DiscoveryImageUi(
    val id: String,
    val width: Int,
    val height: Int,
    val urls: UnsplashUrlUiModel
)

data class UnsplashUrlUiModel(
    val imageUrl: String
)

