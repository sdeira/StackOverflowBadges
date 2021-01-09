package com.example.stackoverflowbadges.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stackoverflowbadges.model.AccessToken
import com.example.stackoverflowbadges.model.Badge
import com.example.stackoverflowbadges.model.Filters
import com.example.stackoverflowbadges.model.RemoteKey

@Database(
    entities = [AccessToken::class, Badge::class, Filters::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class StackOverflowDataBase : RoomDatabase() {

    abstract fun accessTokenDao(): AccessTokenDao

    abstract fun badgeDao(): BadgeDao

    abstract fun remoteKeyDao(): RemoteKeyDao

    abstract fun filtersDao(): FiltersDao
}