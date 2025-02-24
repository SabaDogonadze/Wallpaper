package com.example.wallpaperapp.data.discovery

import com.squareup.moshi.Json

data class DiscoveryImageResponse(
    @Json(name = "id")
    val id: String,
    @Json(name ="width")
    val width: Int,
    @Json(name ="height")
    val height: Int,
    @Json(name ="urls")
    val urls: UnsplashURL
)

data class UnsplashURL(
    @Json(name ="regular")
    val imageUrl: String
)
