package com.example.wallpaperapp.data.remote.collection_discovery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.wallpaperapp.data.paging.collection_discovery.CollectionDiscoveryPagingSource
import com.example.wallpaperapp.domain.collection_discovery.CollectionDiscoveryImage
import com.example.wallpaperapp.domain.collection_discovery.CollectionDiscoveryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CollectionDiscoveryRepositoryImpl@Inject constructor(private val collectionDiscoveryService: CollectionDiscoveryService):CollectionDiscoveryRepository {
    override suspend fun getCollectionImages(id:String): Flow<PagingData<CollectionDiscoveryImage>> {
        return Pager(
            config = PagingConfig(pageSize = 20,prefetchDistance = 10, enablePlaceholders = false),  /* page size - how many images should be shown, prefetchDistance - when call next page, in this case when 20 images are shown and 10 left
                                                                                                           next page will be called, enablePlaceholders -  Paging won’t show placeholders for unloaded data. */
            pagingSourceFactory = { CollectionDiscoveryPagingSource(collectionDiscoveryService, id = id ) }
        ).flow.map { pagingData ->
            pagingData.map { response ->
                response.toDomain()
            }
        }
    }
}