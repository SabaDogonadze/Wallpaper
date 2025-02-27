package com.example.wallpaperapp.data.collection

import com.squareup.moshi.Json

data class CollectionSearchResponse(
    @Json(name = "total") val total: Int,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "results") val results: List<CollectionModelDto>
)
