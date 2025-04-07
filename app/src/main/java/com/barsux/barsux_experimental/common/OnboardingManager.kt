package com.barsux.barsux_experimental.common

import android.content.Context
import android.content.SharedPreferences

object OnboardingManager {
    private const val PREF_NAME = "app_prefs"
    private const val ONBOARDING_COMPLETED = "onboarding_completed"

    fun isOnboardingCompleted(context: Context): Boolean {
        val prefs = getPrefs(context)
        return prefs.getBoolean(ONBOARDING_COMPLETED, false)
    }

    fun setOnboardingCompleted(context: Context) {
        getPrefs(context).edit().putBoolean(ONBOARDING_COMPLETED, true).apply()
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}
