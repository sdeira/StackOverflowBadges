package com.example.stackoverflowbadges.di

import com.example.stackoverflowbadges.api.TokenApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object TokenApiModule {

    @Singleton
    @Provides
    fun tokenApi(
        okHttpClient: OkHttpClient
    ): TokenApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://stackoverflow.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(TokenApi::class.java)
    }
}