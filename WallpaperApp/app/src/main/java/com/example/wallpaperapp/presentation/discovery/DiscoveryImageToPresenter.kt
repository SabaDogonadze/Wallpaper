package com.example.wallpaperapp.presentation.discovery

import com.example.wallpaperapp.domain.discovery.DiscoveryImageModel
import com.example.wallpaperapp.domain.discovery.UnsplashUrlModel

fun DiscoveryImageModel.toPresenter(): DiscoveryImageUi {
    return DiscoveryImageUi(
        id = this.id, width = 0, height = 0, urls = this.urls.toPresenter()
    )
}

fun UnsplashUrlModel.toPresenter(): UnsplashUrlUiModel {
    return UnsplashUrlUiModel(
        imageUrl = this.imageUrl
    )
}
