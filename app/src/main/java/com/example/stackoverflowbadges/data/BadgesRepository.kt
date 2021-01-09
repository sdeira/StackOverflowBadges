package com.example.stackoverflowbadges.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.example.stackoverflowbadges.api.BadgesApi
import com.example.stackoverflowbadges.db.StackOverflowDataBase
import com.example.stackoverflowbadges.model.Badge
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class BadgesRepository @Inject constructor(
    private val service: BadgesApi,
    private val dataBase: StackOverflowDataBase
) {
    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    /**
     * Get the badges flow paging data to be submitted in the adapter.
     */
    fun getBadgesResultStream(): Flow<PagingData<Badge>> {
        val pagingSourceFactory = {
            dataBase.badgeDao().badges()
        }

        return Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = BadgesRemoteMediator(
                service,
                dataBase
            ),
            pagingSourceFactory = pagingSourceFactory).flow
    }

    /**
     * Set Filter order in DB.
     */
    suspend fun setFilterOrder(order: String) {
        dataBase.withTransaction {
            dataBase.filtersDao().filterOrder(order)
        }
    }

    /**
     * Set Filter max in DB.
     */
    suspend fun setFilterMax(max: String?) {
        dataBase.withTransaction {
            dataBase.filtersDao().filterMax(max)
        }
    }

    /**
     * Set Filter min in DB.
     */
    suspend fun setFilterMin(min: String?) {
        dataBase.withTransaction {
            dataBase.filtersDao().filterMin(min)
        }
    }
}