package com.example.stackoverflowbadges.ui.badges

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.stackoverflowbadges.R
import com.example.stackoverflowbadges.data.BadgesRepository
import com.example.stackoverflowbadges.model.UiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Post View model.
 */
@ExperimentalPagingApi
class BadgesViewModel @ViewModelInject constructor(
    private val repository: BadgesRepository
) : ViewModel() {
    private var currentResult: Flow<PagingData<UiModel.BadgeItem>>? = null

    /**
     * Get Posts from the repository if we don't get it already
     */
    fun getBadges(): Flow<PagingData<UiModel.BadgeItem>> {
        val lastResult = currentResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult = repository.getBadgesResultStream().cachedIn(viewModelScope)
            .map { pagingData -> pagingData.map { UiModel.BadgeItem(it) } }
        currentResult = newResult
        return newResult
    }

    /**
     * Set Filter Order.
     */
    suspend fun setFilterOrder(isChecked: Boolean) {
        if (isChecked) {
            repository.setFilterOrder("asc")
        } else {
            repository.setFilterOrder("desc")
        }

    }

    /**
     * Set Filter By Rank.
     */
    suspend fun setFilterByRank(rankId: Int) {
        when (rankId) {
            R.id.gold_radio_button -> {
                repository.setFilterMax("gold")
                repository.setFilterMin(null)
            }
            R.id.silver_radio_button -> {
                repository.setFilterMax("silver")
                repository.setFilterMin("silver")
            }
            R.id.bronze_radio_button -> {
                repository.setFilterMax(null)
                repository.setFilterMin("bronze")
            }
            R.id.all_radio_button -> {
                repository.setFilterMax(null)
                repository.setFilterMin(null)
            }
        }
    }
}