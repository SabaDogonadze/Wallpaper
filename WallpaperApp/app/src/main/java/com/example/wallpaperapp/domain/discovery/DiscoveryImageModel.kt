package com.example.wallpaperapp.domain.discovery

import com.squareup.moshi.Json

data class DiscoveryImageModel(
    val id: String,
    val width: Int,
    val height: Int,
    val urls: UnsplashUrlModel
)

data class UnsplashUrlModel(
    val imageUrl: String
)

