package com.example.wallpaperapp.data.detail

import android.util.Log.d
import com.example.wallpaperapp.data.common.ApiHelper
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.domain.detail.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(private val detailService: DetailService):DetailRepository {
    override suspend fun getDetailImage(id:String): Flow<Resource<DetailImageModel>> {
      return flow {
            emit(Resource.Loading(true))
          // Make the API call and map the result using your ApiHelper
          val result = ApiHelper.handleHttpRequest(
              apiCall = { detailService.getDetailImage(id)},
              mapper = { dto -> dto.toDomain() }
          )
          emit(result)
          d("detailImage","$result")
      }
    }
}