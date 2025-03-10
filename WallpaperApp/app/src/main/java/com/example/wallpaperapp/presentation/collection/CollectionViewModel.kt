package com.example.wallpaperapp.presentation.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.wallpaperapp.domain.collection.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(private val collectionRepository: CollectionRepository):ViewModel() {

    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: StateFlow<String?> = _searchQuery

    val pagingDataFlow = _searchQuery
        .flatMapLatest { query ->
            if (query.isNullOrBlank()) {
                // When query is empty, return default images
                collectionRepository.getCollections()
            } else {
                //  return search results for the query
                collectionRepository.getCollectionsBySearch(query)
            }
        }.map { pagingData ->
            pagingData.map { collectionModel ->
                collectionModel.toPresenter()
            }
        }
        .cachedIn(viewModelScope)

    fun searchCollections(query: String) {
        _searchQuery.value = query
    }
}