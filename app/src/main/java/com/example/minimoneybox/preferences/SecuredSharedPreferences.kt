package com.example.minimoneybox.preferences


import android.app.Application
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class SecuredSharedPreferences(private val context: Application) {

    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        PREF_NAME,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var token: String
        get() = encryptedSharedPreferences.getString(KEY_TOKEN, "") ?: ""
        set(value) = encryptedSharedPreferences.edit { putString(KEY_TOKEN, value) }

    var isLoggedIn: Boolean
        get() = encryptedSharedPreferences.getBoolean(KEY_LOGGED_IN, false)
        set(value) = encryptedSharedPreferences.edit { putBoolean(KEY_LOGGED_IN, value) }

    fun clear() {
        token = ""
        isLoggedIn = false
    }

    companion object {
        private const val PREF_NAME = "money_box_test_app_prefs"
        private const val KEY_TOKEN = "token"
        private const val KEY_LOGGED_IN = "logged_in"
    }
}