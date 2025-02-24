package com.example.wallpaperapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.datastore.SessionTracker
import com.example.wallpaperapp.domain.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository)  : ViewModel() {
    fun readSession() {
        viewModelScope.launch { // data store must have i think its own module which is not implemented, so i keep this viewmodel as it is now
            dataStoreRepository.readEmail().collect {
                SessionTracker.emitSessionState(it.isNotEmpty())
            }
        }
    }
}