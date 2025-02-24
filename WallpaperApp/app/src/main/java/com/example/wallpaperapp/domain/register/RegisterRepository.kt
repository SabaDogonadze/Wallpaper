package com.example.wallpaperapp.domain.register

import com.example.wallpaperapp.data.common.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(email:String,password:String): Flow<Resource<FirebaseUser>>
}