package com.example.wallpaperapp.domain.collection_discovery

data class CollectionDiscoveryImage(
    val id:String,
    val width: Int,
    val height: Int,
    val urls: CollectionDiscoveryUnsplashURL
)

data class CollectionDiscoveryUnsplashURL(
    val imageUrl: String
)
