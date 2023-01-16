package com.example.bettinapp.data.remote.dto

import com.example.bettinapp.domain.model.Match
import com.google.gson.annotations.SerializedName

data class MatchDto(
    @SerializedName("team1")
    val team1: String,
    @SerializedName("team2")
    val team2: String
)

fun MatchDto.toMatch(): Match {
    return Match(
        team1 = team1,
        team2 = team2
    )
}