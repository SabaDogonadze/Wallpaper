package com.example.wallpaperapp.domain.discovery

data class DiscoveryImageModel(
    val id: String,
    val width: Int,
    val height: Int,
    val urls: UnsplashUrlModel
)

data class UnsplashUrlModel(
    val imageUrl: String
)

