package com.example.wallpaperapp.data.remote.discovery.search

import com.example.wallpaperapp.data.remote.discovery.DiscoveryImageResponse
import com.squareup.moshi.Json

data class SearchImageResponse(
    @Json(name = "total") val total: Int,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "results") val results: List<DiscoveryImageResponse>
)
