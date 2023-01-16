package com.example.bettinapp.presentation

sealed class Screen(val route: String) {
    object MatchListScreen: Screen("match_list_screen")
    object MatchResultScreen: Screen("match_results_screen")
}
