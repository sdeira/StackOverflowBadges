package com.example.stackoverflowbadges.api

import com.example.stackoverflowbadges.model.Badge
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface BadgesApi {

    @GET("2.2/me/badges")
    suspend fun getBadges(
        @Query("access_token") accessToken: String?,
        @Query("key") key: String,
        @Query("pagesize") pageSize: Int,
        @Query("page") page: Int?,
        @Query("order") order: String? = null,
        @Query("sort") sort: String? = null,
        @Query("min") min: String? = null,
        @Query("max") max: String? = null,
        @Query("site") site: String? = null,
        @Query("filter") filter: String? = null
    ): ListingData

    /**
     * The listing data of stack overflow badges.
     */
    data class ListingData(
        val items: List<Badge>,
        @SerializedName("has_more")
        val hasMore: Boolean,
        @SerializedName("quota_max")
        val quotaMax: String?,
        @SerializedName("quota_remaining")
        val quotaRemaining: String?
    )
}