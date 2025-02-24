package com.example.wallpaperapp.domain.discovery

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow


interface DiscoveryRepository {
    fun getDiscoveryImage(): Flow<PagingData<DiscoveryImageModel>>
    fun getDiscoverySearchImage(query:String): Flow<PagingData<DiscoveryImageModel>>
}