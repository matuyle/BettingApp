package com.example.bettinapp.presentation.match_list

import com.example.bettinapp.domain.model.Match

sealed class MatchListEvent {
    data class OpenDialog(val matchId: Int) : MatchListEvent()
    data class ConfirmDialog(val match: Match) : MatchListEvent()
    object DismissDialog : MatchListEvent()
    object OnTopButtonPressed : MatchListEvent()
}
