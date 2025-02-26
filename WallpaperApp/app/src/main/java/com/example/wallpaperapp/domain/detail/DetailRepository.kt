package com.example.wallpaperapp.domain.detail

import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.domain.discovery.DiscoveryImageModel
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getDetailImage(id:String): Flow<Resource<DetailImageModel>>
}