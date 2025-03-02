package com.example.wallpaperapp.data.remote.collection_discovery

import com.example.wallpaperapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionDiscoveryService {

    @GET("collections/{id}/photos")
    suspend fun getCollectionImages(
        @Path("id") id: String,
        @Query("page") page: Int = 1,
        @Query("per_page") pageSize: Int,
        @Query("client_id") clientId: String = BuildConfig.API_KEY,
    ): Response<List<CollectionDiscoveryImageDto>>
}