package com.example.stackoverflowbadges.ui.badges

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.stackoverflowbadges.R
import com.example.stackoverflowbadges.databinding.LoadStateFooterBadgeViewItemBinding

/**
 * The View Holder to show the load more view.
 */
class BadgeLoadStateViewHolder(
    private val binding: LoadStateFooterBadgeViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    /**
     * Bind the view with the information
     * @param loadState the load state to check if we have to show an error message or not
     */
    fun bind(loadState: LoadState) {
        binding.loadState = loadState
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): BadgeLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_footer_badge_view_item, parent, false)
            val binding = LoadStateFooterBadgeViewItemBinding.bind(view)
            return BadgeLoadStateViewHolder(binding, retry)
        }
    }
}