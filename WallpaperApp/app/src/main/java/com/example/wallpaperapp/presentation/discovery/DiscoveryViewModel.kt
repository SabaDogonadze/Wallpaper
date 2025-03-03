package com.example.wallpaperapp.presentation.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.data.remote.discovery.DiscoveryImageResponse
import com.example.wallpaperapp.domain.discovery.DiscoveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(private val discoveryRepository: DiscoveryRepository) :
    ViewModel() {

    private val _discoveryImageResponseFlow = MutableStateFlow<Resource<DiscoveryImageUi>?>(null)
    val discoveryImageResponseFlow: StateFlow<Resource<DiscoveryImageUi>?> = _discoveryImageResponseFlow

    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> = _searchQuery
/*    private val _discoverySearchImageResponseFlow = MutableStateFlow<Resource<DiscoveryImageResponse>?>(null)
    val discoverySearchImageResponseFlow: StateFlow<Resource<DiscoveryImageResponse>?> = _discoverySearchImageResponseFlow*/

    /*   val discoveryImageFlow = discoveryRepository.getDiscoveryImage()
           .map { pagingData ->
               pagingData.map { it.to }
           }
           .cachedIn(viewModelScope)*/
    // this need ui model

    val discoveryImageFlow = discoveryRepository.getDiscoveryImage()
        .cachedIn(viewModelScope)
/*
    val discoverySearchImageFlow = discoveryRepository.getDiscoverySearchImage()
        .cachedIn(viewModelScope)*/

   /* val pagingDataFlow = _searchQuery
        .flatMapLatest { query ->
            if (query.isNullOrBlank()) {
                discoveryRepository.getDiscoveryImage()
            } else {
                discoveryRepository.getDiscoverySearchImage(query)
            }
        }
        .cachedIn(viewModelScope)*/

    val pagingDataFlow = _searchQuery
        .flatMapLatest { query ->
            if (query.isNullOrBlank()) {
                // When query is empty, return default images
                discoveryRepository.getDiscoveryImage()
            } else {
                // Otherwise, return search results for the query
                discoveryRepository.getDiscoverySearchImage(query)
            }
        }.map { resource ->
            // Assuming your Resource type has a map function that applies the transformation
            resource.map { discoveryImage ->
                discoveryImage.toPresenter()
            }
        }
        .cachedIn(viewModelScope)

    fun searchImages(query: String) {
        _searchQuery.value = query
    }

}