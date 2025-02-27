package com.example.wallpaperapp.domain.collection

data class CollectionModel(
    val id: String,
    val title: String,
    val urls: UnsplashCollectionURL
)

data class UnsplashCollectionURL(
    val imageUrl: String
)

