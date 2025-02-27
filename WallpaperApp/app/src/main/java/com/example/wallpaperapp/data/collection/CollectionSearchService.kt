package com.example.wallpaperapp.data.collection

import com.example.wallpaperapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CollectionSearchService {
    @GET("search/collections")
    suspend fun getPhotosBySearch(
        @Query("query") query:String,
        @Query("page") page:Int,
        @Query("per_page") pageSize: Int,
        @Query("client_id") clientId: String = BuildConfig.API_KEY
    ): Response<CollectionSearchResponse>
}