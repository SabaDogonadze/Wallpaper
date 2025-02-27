package com.example.wallpaperapp.domain.collection

import androidx.paging.PagingData
import com.example.wallpaperapp.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    suspend fun getCollections(): Flow<PagingData<CollectionModel>>
    suspend fun getCollectionsBySearch(query:String): Flow<PagingData<CollectionModel>>

}