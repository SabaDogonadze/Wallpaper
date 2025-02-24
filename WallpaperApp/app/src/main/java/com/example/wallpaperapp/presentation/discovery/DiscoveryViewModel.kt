package com.example.wallpaperapp.presentation.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.data.discovery.DiscoveryImageResponse
import com.example.wallpaperapp.domain.discovery.DiscoveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel@Inject constructor(private val discoveryRepository:DiscoveryRepository):ViewModel() {

    private val _discoveryImageResponseFlow = MutableStateFlow<Resource<DiscoveryImageResponse>?>(null)
    val discoveryImageResponseFlow : StateFlow<Resource<DiscoveryImageResponse>?> = _discoveryImageResponseFlow

 /*   val discoveryImageFlow = discoveryRepository.getDiscoveryImage()
        .map { pagingData ->
            pagingData.map { it.to }
        }
        .cachedIn(viewModelScope)*/
    // this need ui model

 val discoveryImageFlow = discoveryRepository.getDiscoveryImage()
     .cachedIn(viewModelScope)
}