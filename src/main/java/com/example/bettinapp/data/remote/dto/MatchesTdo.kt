package com.example.bettinapp.data.remote.dto

import com.example.bettinapp.domain.model.Matches
import com.google.gson.annotations.SerializedName

data class MatchesTdo(
    @SerializedName("matches")
    val matches: List<MatchDto>
)

fun MatchesTdo.toMatches(): Matches {
    return Matches(
        matches = matches.map { it.toMatch() }
    )
}
