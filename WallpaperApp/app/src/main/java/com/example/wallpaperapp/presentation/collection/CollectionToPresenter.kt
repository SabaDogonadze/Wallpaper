package com.example.wallpaperapp.presentation.collection

import com.example.wallpaperapp.domain.collection.CollectionModel
import com.example.wallpaperapp.domain.collection.UnsplashCollectionURL

fun CollectionModel.toPresenter():CollectionUi {
    return CollectionUi(
        id = this.id, title = this.title, urls = this.urls.toPresenter()
    )
}

fun UnsplashCollectionURL.toPresenter():UnsplashCollectionUiURL {
    return UnsplashCollectionUiURL(
        imageUrl = this.imageUrl
    )
}