package com.barsux.barsux_experimental.presentation.adapters

data class OnboardingItem(
    val imageRes: Int,
    val title: String,
    val description: String,
    val isLast: Boolean = false
)