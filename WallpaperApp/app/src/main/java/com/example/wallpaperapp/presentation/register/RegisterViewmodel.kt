package com.example.wallpaperapp.presentation.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.R
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.domain.datastore.DataStoreRepository
import com.example.wallpaperapp.domain.register.RegisterRepository
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
class RegisterViewmodel @Inject constructor(private val registerRepository: RegisterRepository,private val dataStoreRepository: DataStoreRepository) :
    ViewModel() {
    private val _userRegisterResponseFlow = MutableStateFlow<ResourceUi<FirebaseUser>?>(null)
    val userRegisterResponseFlow: StateFlow<ResourceUi<FirebaseUser>?> =_userRegisterResponseFlow

    private val _languageFlow = MutableStateFlow("en")
    val languageFlow: StateFlow<String> = _languageFlow

    fun registerUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            registerRepository.register(email, password).collect {
                when (it) {
                    is Resource.Loading -> {
                        _userRegisterResponseFlow.value = ResourceUi.Loading(it.loading)
                    }

                    is Resource.Success -> {
                        _userRegisterResponseFlow.value =
                            ResourceUi.Success(dataSuccess = it.dataSuccess!!)
                    }

                    is Resource.Error -> {
                        _userRegisterResponseFlow.value = ResourceUi.Error(mapFirebaseError(it.errorMessage).toString())
                    }
                }
            }
        }
    }

    fun validateUserInputs(email:String,password:String,repeatPassword:String):Int?{
        return when {
            email.isBlank() -> R.string.email_cannot_be_empty
            !isValidEmail(email) -> R.string.invalid_email_format
            password !=repeatPassword -> R.string.passwords_are_not_the_same
            password.isBlank() -> R.string.password_cannot_be_empty
            password.length < 5 -> R.string.password_must_be_at_least_5_characters_long
            else -> null  // No error
        }
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

    private fun mapFirebaseError(errorMessage: String): Int {
        return when {
            errorMessage.contains("There is no user record") -> R.string.no_account_found_with_this_email
            errorMessage.contains("password is invalid") ->R.string.incorrect_password_please_try_again
            errorMessage.contains("network error") -> R.string.network_error_please_check_your_internet_connection
            else -> R.string.unknown_error
        }
    }
}