package com.example.stackoverflowbadges.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stackoverflowbadges.model.AccessToken
import com.example.stackoverflowbadges.model.Badge

@Database(
    entities = [AccessToken::class, Badge::class],
    version = 1,
    exportSchema = false
)
abstract class StackOverflowDataBase : RoomDatabase() {

    abstract fun accessTokenDao(): AccessTokenDao

    abstract fun badgeDat(): BadgeDao
}