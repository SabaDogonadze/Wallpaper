package com.example.wallpaperapp.presentation.collection_discovery

data class CollectionDiscoveryImageUi(
    val id:String,
    val width: Int,
    val height: Int,
    val urls: CollectionDiscoveryUiUnsplashURL
)

data class CollectionDiscoveryUiUnsplashURL(
    val imageUrl: String
)
