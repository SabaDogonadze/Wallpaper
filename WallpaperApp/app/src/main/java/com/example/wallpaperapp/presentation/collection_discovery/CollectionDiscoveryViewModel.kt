package com.example.wallpaperapp.presentation.collection_discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.domain.collection_discovery.CollectionDiscoveryImage
import com.example.wallpaperapp.domain.collection_discovery.CollectionDiscoveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CollectionDiscoveryViewModel @Inject constructor(private val collectionDiscoveryRepository: CollectionDiscoveryRepository):ViewModel(){

    private val _collectionDiscoveryImageFlow = MutableStateFlow<Resource<CollectionDiscoveryImageUi>?>(null)
    val collectionDiscoveryImageFlow: StateFlow<Resource<CollectionDiscoveryImageUi>?> = _collectionDiscoveryImageFlow

    suspend fun getDiscoveryImages(id: String) =
        collectionDiscoveryRepository.getCollectionImages(id)
            .map { pagingData ->
                pagingData.map { collectionDiscoveryModel ->
                    collectionDiscoveryModel.toPresenter()
                }
            }
            .cachedIn(viewModelScope)

}