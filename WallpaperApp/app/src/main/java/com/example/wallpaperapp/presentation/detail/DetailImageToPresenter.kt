package com.example.wallpaperapp.presentation.detail

import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.domain.detail.DetailUnsplashURL

fun DetailImageModel.toPresenter(): DetailImageUi {
    return DetailImageUi(
        id = this.id, width = 0, height = 0, urls = this.urls.toPresenter()
    )
}

fun DetailUnsplashURL.toPresenter(): DetailUnsplashUiURL {
    return DetailUnsplashUiURL(
        imageUrl = this.imageUrl
    )
}
