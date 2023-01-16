package com.example.bettinapp.presentation.match_list

import com.example.bettinapp.domain.model.Match

sealed class MatchListDialogEvent {
    data class OpenDialog(val match: Match): MatchListDialogEvent()
    data class ConfirmDialog(val match: Match) : MatchListDialogEvent()
    object DismissDialog: MatchListDialogEvent()
}
