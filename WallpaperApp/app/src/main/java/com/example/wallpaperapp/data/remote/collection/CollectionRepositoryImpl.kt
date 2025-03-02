package com.example.wallpaperapp.data.remote.collection

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.wallpaperapp.data.paging.collection.CollectionPagingSource
import com.example.wallpaperapp.data.paging.collection.CollectionSearchPagingSource
import com.example.wallpaperapp.domain.collection.CollectionModel
import com.example.wallpaperapp.domain.collection.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(private val collectionService: CollectionService, private val collectionSearchService: CollectionSearchService):CollectionRepository {
    override suspend fun getCollections(): Flow<PagingData<CollectionModel>> {
        return Pager(
            config = PagingConfig(pageSize = 30,prefetchDistance = 10, enablePlaceholders = false),  /* page size - how many images should be shown, prefetchDistance - when call next page, in this case when 20 images are shown and 10 left
                                                                                                           next page will be called, enablePlaceholders -  Paging won’t show placeholders for unloaded data. */
            pagingSourceFactory = { CollectionPagingSource(collectionService) }
        ).flow.map { pagingData ->
            pagingData.map { response ->
                response.toDomain()
            }
        }
    }

    override suspend fun getCollectionsBySearch(query:String): Flow<PagingData<CollectionModel>> {
        return Pager(
            config = PagingConfig(pageSize = 30,prefetchDistance = 10, enablePlaceholders = false),  /* page size - how many images should be shown, prefetchDistance - when call next page, in this case when 20 images are shown and 10 left
                                                                                                           next page will be called, enablePlaceholders -  Paging won’t show placeholders for unloaded data. */
            pagingSourceFactory = { CollectionSearchPagingSource(collectionSearchService, query = query ) }
        ).flow.map { pagingData ->
            pagingData.map { response ->
                response.toDomain()
            }
        }
    }

}