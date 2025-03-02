package com.example.wallpaperapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.domain.detail.DetailRepository
import com.example.wallpaperapp.domain.favourite.LocalFavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val detailRepository: DetailRepository,private val favouriteRepository: LocalFavouriteRepository):ViewModel() {

    private val _detailImageResponseFlow = MutableStateFlow<Resource<DetailImageModel>?>(null)
    val detailImageResponseFlow: StateFlow<Resource<DetailImageModel>?> = _detailImageResponseFlow

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun checkIfFavorite(imageId: String) {
        viewModelScope.launch {
            favouriteRepository.getAll()
                .map { list -> list.any { it.id == imageId } } // Check if the image is in the favorites
                .collect { isFav ->
                    _isFavorite.value = isFav
                }
        }
    }

    fun addFavourite(image: DetailImageModel) {
        viewModelScope.launch {
            favouriteRepository.insert(image)
            _isFavorite.value = true
        }
    }

    fun removeFavourite(image: DetailImageModel) {
        viewModelScope.launch {
            favouriteRepository.delete(image)
            _isFavorite.value = false
        }
    }

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