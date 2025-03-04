package com.example.wallpaperapp.presentation.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.domain.favourite.LocalFavouriteRepository
import com.example.wallpaperapp.presentation.detail.DetailImageUi
import com.example.wallpaperapp.presentation.detail.toDomain
import com.example.wallpaperapp.presentation.detail.toPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel@Inject constructor(private val favouriteRepository: LocalFavouriteRepository):ViewModel() {

    val favouriteImages: Flow<List<DetailImageUi>> = favouriteRepository.getAll().map { list -> list.map { it.toPresenter() } }.flowOn(
        Dispatchers.IO)

    fun addFavourite(image: DetailImageUi) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteRepository.insert(image.toDomain())
        }
    }

    fun removeFavourite(image: DetailImageUi) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteRepository.delete(image.toDomain())
        }
    }
}