package com.example.stackoverflowbadges.di

import com.example.stackoverflowbadges.api.BadgesApi
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
object BadgesApiModule {

    @Singleton
    @Provides
    fun redditApi(
        okHttpClient: OkHttpClient
    ): BadgesApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.stackexchange.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(BadgesApi::class.java)
    }
}