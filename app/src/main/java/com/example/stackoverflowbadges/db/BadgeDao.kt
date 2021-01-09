package com.example.stackoverflowbadges.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stackoverflowbadges.model.Badge

@Dao
interface BadgeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Badge>)

    @Query("SELECT * FROM badges ORDER BY indexInResponse ASC")
    fun badges(): PagingSource<Int, Badge>
}