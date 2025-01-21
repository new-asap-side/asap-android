package com.asap.data.local.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionLocalDataSourceImpl @Inject constructor(
    private val sessionDataStore: DataStore<Preferences>
) : SessionLocalDataSource {

    override suspend fun getAccessToken(): String? {
        return sessionDataStore.data.map { pref ->
            pref[ACCESS_TOKEN]
        }.firstOrNull()
    }

    override suspend fun getRefreshToken(): String? {
        return sessionDataStore.data.map { pref ->
            pref[REFRESH_TOKEN]
        }.firstOrNull()
    }

    override suspend fun getFCMToken(): String? {
        return sessionDataStore.data.map { pref ->
            pref[FCM_TOKEN]
        }.firstOrNull()
    }

    override suspend fun registerFCMToken(token: String) {
        sessionDataStore.edit { store ->
            store[FCM_TOKEN] = token
        }
    }

    override suspend fun updateAccessToken(accessToken: String) {
        sessionDataStore.edit {
            it[ACCESS_TOKEN] = accessToken
        }
    }

    override suspend fun updateRefreshToken(refreshToken: String) {
        sessionDataStore.edit {
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun clear() {
        sessionDataStore.edit {
            with (it) {
                remove(ACCESS_TOKEN)
                remove(REFRESH_TOKEN)
                remove(FCM_TOKEN)
            }
        }
    }

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("accessToken")
        val REFRESH_TOKEN = stringPreferencesKey("refreshToken")
        val FCM_TOKEN = stringPreferencesKey("fcmToken")
    }
}