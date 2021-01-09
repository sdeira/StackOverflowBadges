package com.example.stackoverflowbadges.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stackoverflowbadges.db.StackOverflowDataBase

class SplashViewModel @ViewModelInject constructor(
    private val stackOverflowDataBase: StackOverflowDataBase
) : ViewModel() {
    val uiState: MutableLiveData<SplashUIState> = MutableLiveData()

    suspend fun checkUserToken() {
        val accessTokenSaved = stackOverflowDataBase.accessTokenDao().token(1)
        if (accessTokenSaved != null) {
            uiState.value = SplashUIState.Success
        } else {
            uiState.value = SplashUIState.LoginIntent
        }
    }
}

sealed class SplashUIState {
    object Success : SplashUIState()
    object LoginIntent : SplashUIState()
}