package com.example.stackoverflowbadges.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.POST
import retrofit2.http.Query

interface TokenApi {
    @POST("oauth/access_token/json")
    suspend fun getAccessToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String,
        @Query("redirect_uri") redirectUrl: String,
        @Query("scope") scope: String
    ): AccessToken

    data class AccessToken(
        @SerializedName("access_token")
        val accessToken: String
    )
}