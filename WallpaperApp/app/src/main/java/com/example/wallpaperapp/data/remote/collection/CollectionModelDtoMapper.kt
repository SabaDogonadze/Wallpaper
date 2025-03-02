package com.example.wallpaperapp.data.remote.collection

import com.example.wallpaperapp.domain.collection.CollectionModel
import com.example.wallpaperapp.domain.collection.UnsplashCollectionURL

fun CollectionModelDto.toDomain(): CollectionModel {
    return CollectionModel(
        id = this.id,
        title = this.title,
        urls = this.coverPhoto?.urls?.toDomain() ?: UnsplashCollectionURL("")
    )
}

fun UnsplashCollectionDtoURL.toDomain(): UnsplashCollectionURL {
    return UnsplashCollectionURL(
        imageUrl = this.imageUrl
    )
}