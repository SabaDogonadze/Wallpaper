package com.example.wallpaperapp.data.login

import com.example.wallpaperapp.data.common.ApiHelper
import com.example.wallpaperapp.data.common.Resource
import com.example.wallpaperapp.domain.login.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


class LogInRepositoryImpl: LoginRepository {
    override suspend fun logIn(email: String, password: String): Flow<Resource<FirebaseUser>> {
       return flow {
        emit(Resource.Loading(true))
           try {
               val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).await()
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