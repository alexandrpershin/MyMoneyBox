package com.example.minimoneybox.preferences


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserPreferences(
    private val context: Application,
    private var appPreferencesPreferences: SharedPreferences = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )
) {

    var token: String
        get() = appPreferencesPreferences.getString(KEY_TOKEN, "")!!
        set(value) = appPreferencesPreferences.edit { putString(KEY_TOKEN, value) }

    var isLoggedIn: Boolean
        get() = appPreferencesPreferences.getBoolean(KEY_LOGGED_IN, false)
        set(value) = appPreferencesPreferences.edit { putBoolean(KEY_LOGGED_IN, value) }

    fun clear() {
        token = ""
        isLoggedIn = false
    }

    companion object {
        private const val PREFERENCES_NAME = "money_box_test_app_prefs"
        private const val KEY_TOKEN = "token"
        private const val KEY_LOGGED_IN = "logged_in"
    }
}