package com.example.wallpaperapp.presentation.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.domain.datastore.DataStoreRepository
import com.example.wallpaperapp.domain.login.LoginRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository,private val dataStoreRepository: DataStoreRepository):ViewModel() {
    private val _userLogInResponseFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val userLoginResponseFlow: StateFlow<Resource<FirebaseUser>?> = _userLogInResponseFlow

    private val _languageFlow = MutableStateFlow("en")
    val languageFlow: StateFlow<String> = _languageFlow

    fun userLogIn(email:String,password:String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = loginRepository.logIn(email = email, password = password).collect{
                when(it){
                    is Resource.Loading -> {_userLogInResponseFlow.value = Resource.Loading(it.loading)}
                    is Resource.Success -> {_userLogInResponseFlow.value = Resource.Success(dataSuccess = it.dataSuccess!!)}
                    is Resource.Error -> {_userLogInResponseFlow.value = Resource.Error(it.errorMessage)}
                }
            }
        }
    }

    fun saveEmailAndUserSession(email:String){
        viewModelScope.launch {
            dataStoreRepository.saveEmailAndSession(email)
        }
    }

    fun validateUserInputs(email:String,password:String):Boolean{
        if(!isValidEmail(email)){
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