package com.example.wallpaperapp.domain.login

import com.example.wallpaperapp.data.common.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow


interface LoginRepository {
    suspend fun logIn(email:String,password:String): Flow<Resource<FirebaseUser>>
}