package com.example.minimoneybox.preferences


import android.app.Application

class SecuredSharedPreferences(private val context: Application) {

    private fun getPreferences(preferencesName: String, cryptoKey: String): EncryptedPreferences {
        return EncryptedPreferences(context, cryptoKey, preferencesName)
    }

    private val appPreferencesPreferences
        get() = getPreferences(
            PREFERENCES_NAME,
            APP_PREFERENCE_CRYPTO
        )

    var token: String
        get() = appPreferencesPreferences.getString(KEY_TOKEN, "")
        set(value) = appPreferencesPreferences.edit().putString(KEY_TOKEN, value).apply()

    var isLoggedIn: Boolean
        get() = appPreferencesPreferences.getBoolean(KEY_LOGGED_IN, false)
        set(value) = appPreferencesPreferences.edit().putBoolean(KEY_LOGGED_IN, value).apply()

    fun clear() {
        token = ""
        isLoggedIn = false
    }

    companion object {
        private const val PREFERENCES_NAME = "money_box_test_app_prefs"
        private const val KEY_TOKEN = "token"
        private const val KEY_LOGGED_IN = "logged_in"
        private const val APP_PREFERENCE_CRYPTO = "db_app_preference"
    }
}