package com.example.stackoverflowbadges.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "badges")
data class Badge(
    @PrimaryKey
    @SerializedName("badge_id")
    val badgeId: String,
    @SerializedName("badge_type")
    val badgeType: String,
    @SerializedName("rank")
    val rank: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("award_count")
    val awardCount: String,
    @SerializedName("num_comments")
    val numComments: Int,
    @SerializedName("description")
    val description: String?
) {
    var indexInResponse: Int = -1
}