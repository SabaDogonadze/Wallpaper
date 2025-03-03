package com.example.wallpaperapp.presentation.common

sealed class ResourceUi<T>(
    val data:T? = null,
    val error:String? = null, // error can be a string, statusCode, so it should be a generic type ( but i have some error in a viewmodel so , it is a string for now)
    val loading:Boolean = false // this could be an object
) {
    data class Success<T>(val dataSuccess: T?) : ResourceUi<T>(data = dataSuccess)
    class Error<T>(val errorMessage: String) : ResourceUi<T>(error = errorMessage)
    class Loading<T>( load: Boolean) : ResourceUi<T>(loading = load) // data class is not necessary
}