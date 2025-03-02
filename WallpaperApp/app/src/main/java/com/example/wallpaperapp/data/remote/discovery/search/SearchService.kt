package com.example.wallpaperapp.data.remote.discovery.search

import com.example.wallpaperapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/photos")
    suspend fun getPhotosBySearch(
        @Query("query") query:String,
        @Query("page") page:Int,
        @Query("per_page") pageSize: Int,
        @Query("client_id") clientId: String = BuildConfig.API_KEY
    ): Response<SearchImageResponse>
}