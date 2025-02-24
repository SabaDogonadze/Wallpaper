package com.example.wallpaperapp.data.paging

import android.accounts.NetworkErrorException
import android.util.Log
import android.util.Log.d
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wallpaperapp.BuildConfig
import com.example.wallpaperapp.data.discovery.DiscoveryImageResponse
import com.example.wallpaperapp.data.discovery.DiscoveryService
import kotlinx.coroutines.delay
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class DiscoveryImagePagingSource @Inject constructor(private val discoveryService: DiscoveryService) :
    PagingSource<Int, DiscoveryImageResponse>() {

    override fun getRefreshKey(state: PagingState<Int, DiscoveryImageResponse>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiscoveryImageResponse> {
        return try {
           // delay(2000) // For testing bottom loader visibility
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize
            Log.d("PagingDebug", "Loading page: $currentPage")
            val response = discoveryService.getPhotos(currentPage,pageSize, clientId = BuildConfig.API_KEY)

            if (response.isSuccessful) {
                val photos = response.body() ?: emptyList()
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