package com.example.minimoneybox.preferences


import android.app.Application
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFERENCE_NAME = "money_box_test_app_prefs"

class UserPreferences(private val context: Application) {

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = PREFERENCE_NAME)

    var token: Flow<String> = dataStore.data.map { it[PreferenceKeys.token] ?: "" }

    var isLoggedIn: Flow<Boolean> = dataStore.data.map { it[PreferenceKeys.loggedIn] ?: false }

    suspend fun setToken(token: String) =
        dataStore.edit {
            it[PreferenceKeys.token] = token
        }

    suspend fun setLoggedIn(loggedIn: Boolean) =
        dataStore.edit {
            it[PreferenceKeys.loggedIn] = loggedIn
        }

    suspend fun clear() {
        setToken("")
        setLoggedIn(false)
    }

    object PreferenceKeys {
        val token = preferencesKey<String>("skglaajldv2VS3r2ewfdscz546u5yjrhtg==-_+")
        val loggedIn = preferencesKey<Boolean>("aNXJ123456GFJSLKJ#@#%^KDSJDHSCLv65nj978HDVNJSSH")
    }
}