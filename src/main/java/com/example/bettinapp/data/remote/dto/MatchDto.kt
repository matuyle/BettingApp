package com.example.bettinapp.data.remote.dto

import com.example.bettinapp.data.local.entity.MatchEntity
import com.example.bettinapp.domain.model.Match
import com.google.gson.annotations.SerializedName

data class MatchDto(
    @SerializedName("team1")
    val team1: String,
    @SerializedName("team2")
    val team2: String,
    @SerializedName("team1_points")
    val team1_points: Int? = null,
    @SerializedName("team2_points")
    val team2_points: Int? = null,
) {
    fun toMatchEntity(): MatchEntity {
        return MatchEntity(
            team1 = team1,
            team2 = team2,
            team1_points = team1_points,
            team2_points = team2_points,
            timestamp = System.currentTimeMillis()
        )
    }
}

fun MatchDto.toMatch(): Match {
    return Match(
        team1 = team1,
        team2 = team2,
        team1_points = team1_points,
        team2_points = team2_points,
        timestamp = System.currentTimeMillis()
    )
}
