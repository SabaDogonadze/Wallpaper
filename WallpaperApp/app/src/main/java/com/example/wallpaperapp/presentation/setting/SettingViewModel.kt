package com.example.wallpaperapp.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.datastore.SessionTracker
import com.example.wallpaperapp.domain.datastore.DataStoreRepository
import com.example.wallpaperapp.domain.detail.DetailImageModel
import com.example.wallpaperapp.domain.favourite.LocalFavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository,private val favouriteRepository: LocalFavouriteRepository):
    ViewModel() {

    val favouriteImages: Flow<List<DetailImageModel>> = favouriteRepository.getAll()

    private val _languageFlow = MutableStateFlow("en")
    val languageFlow: StateFlow<String> = _languageFlow

    private val _emailFlow = MutableStateFlow("") // Holds the email
    val emailFlow: StateFlow<String> = _emailFlow

 init {
     readEmail()
 }
    private fun readEmail() {
        viewModelScope.launch {
            dataStoreRepository.readEmail().collect { email ->
                _emailFlow.value = email // Update the email state
            }
        }
    }

    fun clearSession() {
        viewModelScope.launch {
            dataStoreRepository.clearSession()  // data store must have i think its own module which is not implemented, so i keep this viewmodel as it is now
            SessionTracker.emitSessionState(false)
        }
    }

    fun toggleLanguage() {
        viewModelScope.launch(Dispatchers.Default) {
            val currentLanguage = dataStoreRepository.readLanguage().first()
            val newLanguage = if (currentLanguage == "en") "ka" else "en"
            dataStoreRepository.saveLanguage(newLanguage)
            _languageFlow.value = newLanguage
        }
    }
}