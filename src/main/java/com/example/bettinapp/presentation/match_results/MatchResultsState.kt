package com.example.bettinapp.presentation.match_results

import com.example.bettinapp.domain.model.Match

data class MatchResultsState(
    val isLoading: Boolean = false,
    val matches: List<Match> = emptyList(),
    val error: String = ""
)
