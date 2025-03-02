package com.example.wallpaperapp.presentation.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.domain.favourite.LocalFavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel@Inject constructor(private val favouriteRepository: LocalFavouriteRepository):ViewModel() {

    val favouriteImages: Flow<List<DetailImageModel>> = favouriteRepository.getAll()

    fun addFavourite(image: DetailImageModel) {
        viewModelScope.launch {
            favouriteRepository.insert(image)
        }
    }

    fun removeFavourite(image: DetailImageModel) {
        viewModelScope.launch {
            favouriteRepository.delete(image)
        }
    }
}