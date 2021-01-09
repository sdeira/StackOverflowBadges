package com.example.stackoverflowbadges.ui.badges

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stackoverflowbadges.databinding.BadgeViewItemBinding
import com.example.stackoverflowbadges.model.UiModel

class BadgeViewHolder(
    private val binding: BadgeViewItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Bind the view with the information
     * @param badge the badge item information
     */
    fun bind(
        badge: UiModel.BadgeItem?
    ) {
        badge?.apply {
            binding.badgeItem = this
        }
    }

    companion object {
        fun create(parent: ViewGroup): BadgeViewHolder {
            return BadgeViewHolder(
                BadgeViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}