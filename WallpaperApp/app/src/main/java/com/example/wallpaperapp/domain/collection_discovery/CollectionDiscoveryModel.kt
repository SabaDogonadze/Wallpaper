package com.example.wallpaperapp.domain.collection_discovery

import com.example.wallpaperapp.data.detail.DetailUnsplashURLDto
import com.squareup.moshi.Json

data class CollectionDiscoveryImage(
    val id:String,
    val width: Int,
    val height: Int,
    val urls: CollectionDiscoveryUnsplashURL
)

data class CollectionDiscoveryUnsplashURL(
    val imageUrl: String
)
