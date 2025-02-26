package com.example.wallpaperapp.presentation.detail_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.data.discovery.DiscoveryImageResponse
import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.domain.detail.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val detailRepository: DetailRepository):ViewModel() {

    private val _detailImageResponseFlow = MutableStateFlow<Resource<DetailImageModel>?>(null)
    val detailImageResponseFlow: StateFlow<Resource<DetailImageModel>?> = _detailImageResponseFlow

    fun getDetailImage(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = detailRepository.getDetailImage(id).collect {
                when (it) {
                    is Resource.Loading -> {
                        _detailImageResponseFlow.value = Resource.Loading(it.loading)
                    }

                    is Resource.Success -> {
                        _detailImageResponseFlow.value = Resource.Success(dataSuccess = it.dataSuccess!!)
                    }

                    is Resource.Error -> {
                        _detailImageResponseFlow.value = Resource.Error(it.errorMessage)
                    }
                }
            }
        }
    }
}