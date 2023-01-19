package com.example.bettinapp.presentation

import com.example.bettinapp.presentation.common.Constants.MATCH_LIST_SCREEN

data class MainState (
    val isPreferencesLoaded: Boolean = false,
    val screen: String = MATCH_LIST_SCREEN
)