package com.example.wallpaperapp.data.paging.collection

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wallpaperapp.BuildConfig
import com.example.wallpaperapp.data.collection.CollectionModelDto
import com.example.wallpaperapp.data.collection.CollectionSearchResponse
import com.example.wallpaperapp.data.collection.CollectionSearchService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CollectionSearchPagingSource @Inject constructor(
    private val collectionSearchService: CollectionSearchService,
    private val query: String
) : PagingSource<Int, CollectionModelDto>() {

    override fun getRefreshKey(state: PagingState<Int, CollectionModelDto>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionModelDto> {
        return try {
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize
            Log.d("PagingDebug", "Loading page: $currentPage")

            val response = collectionSearchService.getPhotosBySearch(
                query = query,
                page = currentPage,
                pageSize = pageSize,
                clientId = BuildConfig.API_KEY
            )

            if (response.isSuccessful) {
                val searchResponse: CollectionSearchResponse? = response.body()
                // Extract the list of collections from the response.
                val photos = searchResponse?.results ?: emptyList()
                Log.d("PagingDebug", "Loaded ${photos.size} items for page $currentPage")

                // Determine next and previous pages.
                val nextPage = if (photos.isEmpty()) null else currentPage + 1
                val prevPage = if (currentPage == 1) null else currentPage - 1

                LoadResult.Page(
                    data = photos,
                    prevKey = prevPage,
                    nextKey = nextPage
                )
            } else {
                LoadResult.Error(IOException("Unsuccessful response: ${response.code()}"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
