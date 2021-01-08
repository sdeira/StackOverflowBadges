package com.example.stackoverflowbadges.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stackoverflowbadges.model.AccessToken

@Dao
interface AccessTokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accessToken: AccessToken)

    @Query("SELECT * FROM access_token WHERE id = :id")
    suspend fun token(id: Int): AccessToken?
}