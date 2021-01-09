package com.example.stackoverflowbadges.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filters")
class Filters(
    @PrimaryKey
    val id: Int,
    val order: String?,
    val min: String?,
    val max: String?
)