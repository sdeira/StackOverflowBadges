package com.example.stackoverflowbadges.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(ApplicationComponent::class)
object OkHttpModule {

    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }
}