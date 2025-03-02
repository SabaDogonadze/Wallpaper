package com.example.wallpaperapp.data.remote.collection_discovery

import com.example.wallpaperapp.data.detail.DetailUnsplashURLDto
import com.squareup.moshi.Json

data class CollectionDiscoveryImageDto(
    @Json(name = "id")
    val id:String,
    @Json(name ="width")
    val width: Int,
    @Json(name ="height")
    val height: Int,
    @Json(name ="urls")
    val urls: CollectionDiscoveryUnsplashURLDto
)

data class CollectionDiscoveryUnsplashURLDto(
    @Json(name ="regular")
    val imageUrl: String
)
