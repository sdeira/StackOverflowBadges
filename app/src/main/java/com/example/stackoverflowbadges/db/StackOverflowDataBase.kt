package com.example.stackoverflowbadges.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stackoverflowbadges.model.AccessToken

@Database(
    entities = [AccessToken::class],
    version = 1,
    exportSchema = false
)
abstract class StackOverflowDataBase : RoomDatabase() {

    abstract fun accessTokenDao(): AccessTokenDao
}