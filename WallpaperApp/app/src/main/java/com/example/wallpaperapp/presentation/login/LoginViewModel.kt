package com.example.wallpaperapp.presentation.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.R
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.domain.datastore.DataStoreRepository
import com.example.wallpaperapp.domain.login.LoginRepository
import com.example.wallpaperapp.presentation.common.ResourceUi
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
    private val _userLogInResponseFlow = MutableStateFlow<ResourceUi<FirebaseUser>?>(null)
    val userLoginResponseFlow: StateFlow<ResourceUi<FirebaseUser>?> = _userLogInResponseFlow

    private val _languageFlow = MutableStateFlow("en")
    val languageFlow: StateFlow<String> = _languageFlow

    fun userLogIn(email:String,password:String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = loginRepository.logIn(email = email, password = password).collect{
                when(it){
                    is Resource.Loading -> _userLogInResponseFlow.value = ResourceUi.Loading(it.loading)
                    is Resource.Success -> _userLogInResponseFlow.value = ResourceUi.Success(it.dataSuccess!!)
                    is Resource.Error -> _userLogInResponseFlow.value = ResourceUi.Error(mapFirebaseError(it.errorMessage).toString())
                }
            }
        }
    }

    fun saveEmailAndUserSession(email:String){
        viewModelScope.launch {
            dataStoreRepository.saveEmailAndSession(email)
        }
    }

    fun validateUserInputs(email:String,password:String):Int?{
        return when {
            email.isBlank() -> R.string.email_cannot_be_empty
            !isValidEmail(email) -> R.string.invalid_email_format
            password.isBlank() -> R.string.password_cannot_be_empty
            password.length < 5 -> R.string.password_must_be_at_least_5_characters_long
            else -> null  // No error
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun toggleLanguage() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentLanguage = dataStoreRepository.readLanguage().first()
            val newLanguage = if (currentLanguage == "en") "ka" else "en"
            dataStoreRepository.saveLanguage(newLanguage)
            _languageFlow.value = newLanguage
        }
    }

    private fun mapFirebaseError(errorMessage: String): Int {
        return when {
            errorMessage.contains("There is no user record") -> R.string.no_account_found_with_this_email
            errorMessage.contains("password is invalid") -> R.string.incorrect_password_please_try_again
            errorMessage.contains("network error") -> R.string.network_error_please_check_your_internet_connection
            else -> R.string.unknown_error
        }
    }


}