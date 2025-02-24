package com.example.wallpaperapp.presentation.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.domain.datastore.DataStoreRepository
import com.example.wallpaperapp.domain.register.RegisterRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewmodel @Inject constructor(private val registerRepository: RegisterRepository,private val dataStoreRepository: DataStoreRepository) :
    ViewModel() {
    private val _userRegisterResponseFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val userRegisterResponseFlow: StateFlow<Resource<FirebaseUser>?> =_userRegisterResponseFlow

    private val _languageFlow = MutableStateFlow("en")
    val languageFlow: StateFlow<String> = _languageFlow

    fun registerUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            registerRepository.register(email, password).collect {
                when (it) {
                    is Resource.Loading -> {
                        _userRegisterResponseFlow.value = Resource.Loading(it.loading)
                    }

                    is Resource.Success -> {
                        _userRegisterResponseFlow.value =
                            Resource.Success(dataSuccess = it.dataSuccess!!)
                    }

                    is Resource.Error -> {
                        _userRegisterResponseFlow.value = Resource.Error(it.errorMessage)
                    }
                }
            }
        }
    }

    fun validateUserInputs(email:String,password:String,repeatPassword:String):Boolean{
        if(!isValidEmail(email)){
            return false
        }
        if (repeatPassword != password){
            return false
        }
        if (password.length < 5){
            return false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
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