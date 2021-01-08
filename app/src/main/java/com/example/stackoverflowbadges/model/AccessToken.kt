package com.example.stackoverflowbadges.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "access_token")
class AccessToken(
    @PrimaryKey
    val id: Int,
    val accessToken: String
)