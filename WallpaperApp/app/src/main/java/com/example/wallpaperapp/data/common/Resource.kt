package com.example.wallpaperapp.data.common

sealed class Resource<T>(
    val data:T? = null,
    val error:String? = null, // error can be a string, statusCode, so it should be a generic type ( but i have some error in a viewmodel so , it is a string for now)
    val loading:Boolean = false // this could be an object
) {
    data class Success<T>(val dataSuccess: T?) : Resource<T>(data = dataSuccess)
    class Error<T>(val errorMessage: String) : Resource<T>(error = errorMessage)  // data class is not necessary
    class Loading<T>( load: Boolean) : Resource<T>(loading = load) // data class is not necessary

}