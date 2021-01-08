package com.example.stackoverflowbadges.di

import android.content.Context
import androidx.room.Room
import com.example.stackoverflowbadges.db.StackOverflowDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context): StackOverflowDataBase {
        return Room.databaseBuilder(appContext,
            StackOverflowDataBase::class.java, "StackOverflowBadges.db")
            .build()
    }
}