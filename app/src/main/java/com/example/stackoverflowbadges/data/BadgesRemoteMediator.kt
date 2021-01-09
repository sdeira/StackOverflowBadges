package com.example.stackoverflowbadges.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.stackoverflowbadges.api.BadgesApi
import com.example.stackoverflowbadges.db.StackOverflowDataBase
import com.example.stackoverflowbadges.model.Badge
import com.example.stackoverflowbadges.model.Filters
import com.example.stackoverflowbadges.model.RemoteKey
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class BadgesRemoteMediator(
    private val service: BadgesApi,
    private val stackOverflowDataBase: StackOverflowDataBase
) : RemoteMediator<Int, Badge>() {

    companion object {
        private const val REMOTE_KEY_ID = "remoteKey"
        private const val KEY = "w52jhZVRBAv1ImHCSORkGA(("
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Badge>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey =
                        stackOverflowDataBase.withTransaction { stackOverflowDataBase.remoteKeyDao().remoteKey() }
                            ?: return MediatorResult.Success(endOfPaginationReached = true)
                    if (remoteKey.nextPage == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextPage
                }
            }

            var filters = stackOverflowDataBase.filtersDao().getFilter()
            if (filters == null) {
                filters = Filters(1, "desc", null, null)
                stackOverflowDataBase.filtersDao().insert(filters)
            }

            val data = service.getBadgesFromUser(
                "6891563",
                pageSize = when (loadType) {
                    LoadType.REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                },
                page = loadKey,
                order = filters.order,
                sort = "rank",
                min = filters.min,
                max = filters.max,
                site = "stackoverflow",
                key = KEY,
                filter = "!9_bDE.caY"
            )

//            val accessToken = stackOverflowDataBase.accessTokenDao().token()?.accessToken
//            val data = service.getBadges(accessToken, KEY, pageSize = when (loadType) {
//                LoadType.REFRESH -> state.config.initialLoadSize
//                else -> state.config.pageSize
//            }, page = loadKey, order = "desc", sort = "rank", filters.min, filters.max, site = "stackoverflow", filter = "!9_bDE.caY")

            val items = data.items.map { it }
            stackOverflowDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    stackOverflowDataBase.badgeDao().clearBadges()
                    stackOverflowDataBase.remoteKeyDao().delete()
                }

                if (data.hasMore) {
                    stackOverflowDataBase.remoteKeyDao().insert(RemoteKey(REMOTE_KEY_ID, loadKey + 1))
                }
                stackOverflowDataBase.badgeDao().insertAll(items)
            }
            return MediatorResult.Success(endOfPaginationReached = !data.hasMore)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}