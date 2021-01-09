package com.example.stackoverflowbadges.ui.badges

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class BadgesLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<BadgeLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: BadgeLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BadgeLoadStateViewHolder {
        return BadgeLoadStateViewHolder.create(parent, retry)
    }
}