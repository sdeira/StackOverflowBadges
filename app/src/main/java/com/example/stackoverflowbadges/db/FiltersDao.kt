package com.example.stackoverflowbadges.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stackoverflowbadges.model.Filters

@Dao
interface FiltersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(filters: Filters)

    @Query("SELECT * FROM filters WHERE id = 1")
    suspend fun getFilter(): Filters?

    @Query("UPDATE filters SET `order`=:order WHERE id = 1")
    suspend fun filterOrder(order: String)

    @Query("UPDATE filters SET max=:max WHERE id = 1")
    suspend fun filterMax(max: String?)

    @Query("UPDATE filters SET min=:min WHERE id = 1")
    suspend fun filterMin(min: String?)

    @Query("DELETE FROM filters")
    suspend fun delete()
}