package com.thit.movieapp.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class UserPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val TAG: String = javaClass.name
    private val applicationContext = context.applicationContext

    val authToken: Flow<String?>
        get() = applicationContext.dataStore.data.map { prefs -> prefs[KEY_AUTH] }
    val userName: Flow<String?>
        get() = applicationContext.dataStore.data.map { prefs -> prefs[KEY_USER_NAME] }
    val userID: Flow<Int?>
        get() = applicationContext.dataStore.data.map { prefs -> prefs[KEY_USER_ID] }

    suspend fun saveAuthUserInfo(authToken: String, userName: String, userId: Int) {
        applicationContext.dataStore.edit { mutablePreferences ->
            mutablePreferences[KEY_AUTH] = authToken
            mutablePreferences[KEY_USER_NAME] = userName
            mutablePreferences[KEY_USER_ID] = userId
        }
    }
    companion object {
        private val KEY_AUTH = stringPreferencesKey("authTokenKey")
        private val KEY_USER_NAME = stringPreferencesKey("userNameKey")
        private val KEY_USER_ID = intPreferencesKey("userIdKey")
    }
}