package com.example.bettinapp.presentation.match_results

sealed class MatchResultsEvent {
    object OnTopButtonPressed: MatchResultsEvent()
    object OnNav: MatchResultsEvent()
}
