package com.example.wallpaperapp.data.collection

import com.squareup.moshi.Json

data class CollectionModelDto(
    @Json(name = "id")
    val id: String,
    @Json(name ="title")
    val title: String,
    @Json(name ="cover_photo")
    val coverPhoto: CoverPhoto?
)

data class UnsplashCollectionDtoURL(
    @Json(name ="regular")
    val imageUrl: String
)

data class CoverPhoto(
    @Json(name ="urls")
    val urls: UnsplashCollectionDtoURL?
)
