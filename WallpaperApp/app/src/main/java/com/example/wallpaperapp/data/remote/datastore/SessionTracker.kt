package com.example.wallpaperapp.data.remote.datastore

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object SessionTracker {
    private val _userSession = MutableSharedFlow<Boolean>(replay = 1)
    val userSession: SharedFlow<Boolean> get() = _userSession

    suspend fun emitSessionState(isLoggedIn: Boolean) {
        _userSession.emit(isLoggedIn)
    }
}