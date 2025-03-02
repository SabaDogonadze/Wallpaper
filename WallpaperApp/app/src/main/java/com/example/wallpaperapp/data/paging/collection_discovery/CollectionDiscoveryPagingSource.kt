package com.example.wallpaperapp.data.paging.collection_discovery

import android.util.Log
import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wallpaperapp.BuildConfig
import com.example.wallpaperapp.data.remote.collection.CollectionModelDto
import com.example.wallpaperapp.data.remote.collection.CollectionService
import com.example.wallpaperapp.data.remote.collection_discovery.CollectionDiscoveryImageDto
import com.example.wallpaperapp.data.remote.collection_discovery.CollectionDiscoveryService
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CollectionDiscoveryPagingSource @Inject constructor(private val collectionDiscoveryService: CollectionDiscoveryService, private val id:String) :
    PagingSource<Int, CollectionDiscoveryImageDto>() {

    override fun getRefreshKey(state: PagingState<Int, CollectionDiscoveryImageDto>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionDiscoveryImageDto> {
        return try {
            // delay(2000) // For testing bottom loader visibility
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize
            Log.d("PagingSource", "Loading page: $currentPage")
            val response = collectionDiscoveryService.getCollectionImages(id = id,currentPage,pageSize,clientId = BuildConfig.API_KEY)
            Log.d("PagingSource", "Loaded items: ${response.body()}")
            if (response.isSuccessful) {
                val photos = response.body() ?: emptyList()
                // If the returned list is empty, assume no more pages are available.
                Log.d("PagingSource", "Loaded ${photos.size} images for page $photos")

                val nextPage = if (photos.isEmpty()) null else currentPage + 1
                val prevPage = if (currentPage == 1) null else currentPage - 1

                LoadResult.Page(
                    data = photos,
                    prevKey = prevPage,
                    nextKey = nextPage
                )
            } else {
                LoadState.Error(IOException("Unsuccessful response or data is null"))
                LoadResult.Error(Exception("Error: ${response.code()}"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}