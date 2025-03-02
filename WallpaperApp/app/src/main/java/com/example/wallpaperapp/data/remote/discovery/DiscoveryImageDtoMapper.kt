package com.example.wallpaperapp.data.remote.discovery

import com.example.wallpaperapp.domain.discovery.DiscoveryImageModel
import com.example.wallpaperapp.domain.discovery.UnsplashUrlModel

fun DiscoveryImageResponse.toDomain(): DiscoveryImageModel {
    return DiscoveryImageModel(
        id = this.id, width =this.width , height = this.height, urls = this.urls.toDomain()
    )
}

fun UnsplashURL.toDomain(): UnsplashUrlModel {
    return UnsplashUrlModel(imageUrl = this.imageUrl)
}