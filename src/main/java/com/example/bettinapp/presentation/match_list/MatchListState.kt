package com.example.bettinapp.presentation.match_list

import com.example.bettinapp.domain.model.Match

data class MatchListState(
    val isLoading: Boolean = false,
    val isBetPlaced: Boolean = false,
    val isDialogOpen: Boolean = false,
    val recentMatch: Match? = null,
    val matches: List<Match> = emptyList(),
    val error: String = ""
)