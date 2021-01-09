package com.example.stackoverflowbadges.ui.bindingsadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.stackoverflowbadges.R

/**
 * Load badge image with rank
 */
@BindingAdapter("colorByRank")
fun colorByRank(view: ImageView, rank: String?) {
    rank?.let {
        when (rank) {
            "bronze" -> view.setImageResource(R.drawable.ic_circle_bronze)
            "silver" -> view.setImageResource(R.drawable.ic_circle_silver)
            "gold" -> view.setImageResource(R.drawable.ic_circle_gold)
        }
    }
}