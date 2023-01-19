package com.example.bettinapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.bettinapp.core.util.Constants.PREFERENCES_NAME
import com.example.bettinapp.domain.repository.DataStoreRepository
import com.example.bettinapp.presentation.common.Constants.MATCH_LIST_SCREEN
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreRepositoryImpl @Inject constructor(
    private val context: Context
) : DataStoreRepository {
    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit {
            it[preferencesKey] = value
        }
    }

    override suspend fun getString(key: String): String {
        val preferenceKey = stringPreferencesKey(key)
        val preference = context.dataStore.data.first()
        preference[preferenceKey] ?: MATCH_LIST_SCREEN
        return preference.toString()
    }
}