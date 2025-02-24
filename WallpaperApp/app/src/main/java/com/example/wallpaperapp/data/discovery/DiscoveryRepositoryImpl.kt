package com.example.wallpaperapp.data.discovery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.wallpaperapp.data.paging.DiscoveryImagePagingSource
import com.example.wallpaperapp.domain.discovery.DiscoveryImageModel
import com.example.wallpaperapp.domain.discovery.DiscoveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiscoveryRepositoryImpl @Inject constructor(private val discoveryService: DiscoveryService) :
    DiscoveryRepository {
    override fun getDiscoveryImage(): Flow<PagingData<DiscoveryImageModel>> {
        return Pager(
            config = PagingConfig(pageSize = 30,prefetchDistance = 10, enablePlaceholders = false),  /* page size - how many images should be shown, prefetchDistance - when call next page, in this case when 20 images are shown and 10 left
                                                                                                           next page will be called, enablePlaceholders -  Paging won’t show placeholders for unloaded data. */
            pagingSourceFactory = { DiscoveryImagePagingSource(discoveryService) }
        ).flow.map { pagingData ->
            pagingData.map { response ->
                response.toDomain()
            }
        }
    }
}

