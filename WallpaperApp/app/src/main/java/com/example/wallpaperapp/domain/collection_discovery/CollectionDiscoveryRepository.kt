package com.example.wallpaperapp.domain.collection_discovery

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface CollectionDiscoveryRepository {
    suspend fun getCollectionImages(id:String): Flow<PagingData<CollectionDiscoveryImage>>
}