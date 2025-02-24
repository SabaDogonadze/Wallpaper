package com.example.wallpaperapp.domain.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveEmailAndSession(email:String)
    suspend fun saveLanguage(language:String)
    fun readEmail(): Flow<String>
    fun readLanguage(): Flow<String>
    suspend fun clearSession()
}


