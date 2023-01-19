package com.example.bettinapp.presentation

import com.example.bettinapp.presentation.common.Constants.MATCH_LIST_SCREEN
import com.example.bettinapp.presentation.common.Constants.MATCH_RESULTS_SCREEN

sealed class Screen(val route: String) {
    object MatchListScreen: Screen(MATCH_LIST_SCREEN)
    object MatchResultScreen: Screen(MATCH_RESULTS_SCREEN)
}
