package com.example.bettinapp

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.bettinapp.core.util.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BettingApplication : Application() {
    private val Context.dataStore by preferencesDataStore(
        name = Constants.PREFERENCES_NAME
    )
}