package com.example.wallpaperapp.data.detail

import com.squareup.moshi.Json

data class DetailImageModelDto(
    @Json(name = "id")
    val id: String,
    @Json(name ="width")
    val width: Int,
    @Json(name ="height")
    val height: Int,
    @Json(name ="urls")
    val urls: DetailUnsplashURLDto
)

data class DetailUnsplashURLDto(
    @Json(name ="regular")
    val imageUrl: String
)

