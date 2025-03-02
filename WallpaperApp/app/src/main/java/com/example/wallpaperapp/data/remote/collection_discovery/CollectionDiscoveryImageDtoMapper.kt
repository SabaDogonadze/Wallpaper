package com.example.wallpaperapp.data.remote.collection_discovery

import com.example.wallpaperapp.domain.collection_discovery.CollectionDiscoveryImage
import com.example.wallpaperapp.domain.collection_discovery.CollectionDiscoveryUnsplashURL

fun CollectionDiscoveryImageDto.toDomain(): CollectionDiscoveryImage {
    return CollectionDiscoveryImage(
        id = this.id, width = this.width, height = this.height, urls = this.urls.toDomain()
    )
}

fun CollectionDiscoveryUnsplashURLDto.toDomain():CollectionDiscoveryUnsplashURL{
    return CollectionDiscoveryUnsplashURL(imageUrl = this.imageUrl)
}