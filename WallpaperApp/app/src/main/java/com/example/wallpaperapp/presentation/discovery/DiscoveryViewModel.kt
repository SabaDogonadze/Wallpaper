package com.example.wallpaperapp.presentation.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.data.remote.discovery.DiscoveryImageResponse
import com.example.wallpaperapp.domain.discovery.DiscoveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(private val discoveryRepository: DiscoveryRepository) :
    ViewModel() {

    private val _discoveryImageResponseFlow = MutableStateFlow<Resource<DiscoveryImageResponse>?>(null)
    val discoveryImageResponseFlow: StateFlow<Resource<DiscoveryImageResponse>?> = _discoveryImageResponseFlow

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
        }
        .cachedIn(viewModelScope)

    fun searchImages(query: String) {
        _searchQuery.value = query
    }

}