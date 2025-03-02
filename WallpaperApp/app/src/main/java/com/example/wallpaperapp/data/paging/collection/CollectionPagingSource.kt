package com.example.wallpaperapp.data.paging.collection

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wallpaperapp.BuildConfig
import com.example.wallpaperapp.data.remote.collection.CollectionModelDto
import com.example.wallpaperapp.data.remote.collection.CollectionService
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class CollectionPagingSource@Inject constructor(private val collectionService: CollectionService) :
PagingSource<Int, CollectionModelDto>() {

    override fun getRefreshKey(state: PagingState<Int, CollectionModelDto>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionModelDto> {
        return try {
            // delay(2000) // For testing bottom loader visibility
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize
            Log.d("PagingDebug", "Loading page: $currentPage")
            val response = collectionService.getCollections(currentPage,pageSize, clientId = BuildConfig.API_KEY)

            if (response.isSuccessful) {
                val photos = response.body() ?: emptyList()
                photos.forEach { collection ->
                    Log.d("CollectionID", "Collection ID: ${collection.id}")
                }
                // If the returned list is empty, assume no more pages are available.
                Log.d("PagingDebug", "Loaded ${photos.size} images for page $photos")

                val nextPage = if (photos.isEmpty()) null else currentPage + 1
                val prevPage = if (currentPage == 1) null else currentPage - 1

                LoadResult.Page(
                    data = photos,
                    prevKey = prevPage,
                    nextKey = nextPage
                )
            } else {
                LoadResult.Error(IOException("Unsuccessful response or data is null"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}