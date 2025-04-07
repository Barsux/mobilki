package com.barsux.barsux_experimental.common

import android.content.Context
import android.content.SharedPreferences

object AuthManager {
    private const val PREF_NAME = "auth_prefs"
    private const val USER_EMAIL = "user_email"

    fun login(context: Context, email: String) {
        getPrefs(context).edit().putString(USER_EMAIL, email).apply()
    }

    fun logout(context: Context) {
        getPrefs(context).edit().remove(USER_EMAIL).apply()
    }

    fun getLoggedInEmail(context: Context): String? {
        return getPrefs(context).getString(USER_EMAIL, null)
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}
