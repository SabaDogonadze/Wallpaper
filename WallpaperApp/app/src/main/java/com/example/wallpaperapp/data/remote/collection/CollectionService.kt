package com.example.wallpaperapp.data.remote.collection

import com.example.wallpaperapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CollectionService {
    @GET("collections")
    suspend fun getCollections(
        @Query("page") page: Int = 1,
        @Query("per_page") pageSize: Int,
        @Query("client_id") clientId: String = BuildConfig.API_KEY
    ): Response<List<CollectionModelDto>>
}