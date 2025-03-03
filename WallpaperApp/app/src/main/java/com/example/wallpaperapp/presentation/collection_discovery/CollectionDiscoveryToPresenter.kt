package com.example.wallpaperapp.presentation.collection_discovery

import com.example.wallpaperapp.domain.collection_discovery.CollectionDiscoveryImage
import com.example.wallpaperapp.domain.collection_discovery.CollectionDiscoveryUnsplashURL

fun CollectionDiscoveryImage.toPresenter(): CollectionDiscoveryImageUi {
    return CollectionDiscoveryImageUi(
        id = this.id, width = 0, height = 0, urls = this.urls.toPresenter()
        )
}

fun CollectionDiscoveryUnsplashURL.toPresenter(): CollectionDiscoveryUiUnsplashURL {
    return CollectionDiscoveryUiUnsplashURL(
        imageUrl = this.imageUrl
    )
}
