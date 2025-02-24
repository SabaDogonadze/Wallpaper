package com.example.wallpaperapp.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.datastore.SessionTracker
import com.example.wallpaperapp.domain.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository):
    ViewModel() {
    fun clearSession() {
        viewModelScope.launch {
            dataStoreRepository.clearSession()  // data store must have i think its own module which is not implemented, so i keep this viewmodel as it is now
            SessionTracker.emitSessionState(false)
        }
    }
}