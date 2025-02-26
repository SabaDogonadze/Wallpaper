package com.example.wallpaperapp.data.detail

import com.example.wallpaperapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailService {
    @GET("photos/{id}")
    suspend fun getDetailImage(
        @Path("id") id: String,
        @Query("client_id") clientId: String = BuildConfig.API_KEY
    ):Response<DetailImageModelDto>
}