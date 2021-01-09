package com.example.stackoverflowbadges.ui.badges

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.stackoverflowbadges.model.UiModel
import javax.inject.Inject

/**
 * Badge list paging adapter.
 */
class BadgesAdapter @Inject constructor() : PagingDataAdapter<UiModel.BadgeItem, BadgeViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        val uiModel = getItem(position)
        holder.bind(uiModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        return BadgeViewHolder.create(parent)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<UiModel.BadgeItem>() {
            override fun areContentsTheSame(oldItem: UiModel.BadgeItem, newItem: UiModel.BadgeItem): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: UiModel.BadgeItem, newItem: UiModel.BadgeItem): Boolean =
                oldItem.badge.badgeId == newItem.badge.badgeId
        }
    }
}