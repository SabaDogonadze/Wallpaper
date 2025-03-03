package com.example.wallpaperapp.presentation.collection

data class CollectionUi(
    val id: String,
    val title: String,
    val urls: UnsplashCollectionUiURL
)

data class UnsplashCollectionUiURL(
    val imageUrl: String
)
