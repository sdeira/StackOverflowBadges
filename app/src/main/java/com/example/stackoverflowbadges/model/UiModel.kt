package com.example.stackoverflowbadges.model

/**
 * Sealed class to be used only in the ui.
 */
sealed class UiModel {
    data class BadgeItem(val badge: Badge) : UiModel()
}