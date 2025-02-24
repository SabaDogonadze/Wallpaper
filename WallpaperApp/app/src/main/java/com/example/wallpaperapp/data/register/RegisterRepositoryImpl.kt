package com.example.wallpaperapp.data.register

import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.domain.register.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


class RegisterRepositoryImpl : RegisterRepository {
    override suspend fun register(email: String, password: String): Flow<Resource<FirebaseUser>> {
    return flow {
        emit(Resource.Loading(true))
        try {
            val authResult = FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .await()
            val firebaseUser = authResult.user
            if (firebaseUser != null){
                emit(Resource.Success(firebaseUser))
            }else{
                emit(Resource.Error("User Not Found"))
            }
        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage?:"Unexpected Error "))
        }
    }
    }
}