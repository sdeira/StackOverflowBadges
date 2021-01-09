package com.example.stackoverflowbadges.ui.deeplink

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stackoverflowbadges.api.TokenApi
import com.example.stackoverflowbadges.db.StackOverflowDataBase
import com.example.stackoverflowbadges.model.AccessToken
import retrofit2.HttpException
import java.io.IOException

class DeepLinkViewModel @ViewModelInject constructor(
    private val service: TokenApi,
    private val stackOverflowDataBase: StackOverflowDataBase
) : ViewModel() {

    val uiState: MutableLiveData<UIState> = MutableLiveData()

    suspend fun login(data: Uri?, clientId: String, clientSecret: String, redirectUrl: String,
                      scope: String, loginSuccessfully: String) {
        try {
            uiState.value = UIState.Loading(true)
            uiState.value = UIState.FullScreenError(false)

            data?.getQueryParameter("code")?.let {
                val accessToken = service.getAccessToken(
                    clientId,
                    clientSecret,
                    it,
                    redirectUrl,
                    scope
                )
                val accessTokenDB = AccessToken(1, accessToken.accessToken)
                stackOverflowDataBase.accessTokenDao().insert(accessTokenDB)
                uiState.value = UIState.Loading(false)
                uiState.value = UIState.SnackBar(loginSuccessfully)
                uiState.value = UIState.Success

            }
        }  catch (exception: IOException) {
            uiState.value = UIState.Loading(false)
            uiState.value = UIState.FullScreenError(true)
        } catch (exception: HttpException) {
            uiState.value = UIState.Loading(false)
            uiState.value = UIState.FullScreenError(true)
        }

    }
}

sealed class UIState {
    object Success : UIState()
    data class SnackBar(val text: String?) : UIState()
    data class Loading(val show: Boolean) : UIState()
    data class FullScreenError(val show: Boolean) : UIState()
}