package com.example.bettinapp.domain.model

data class MatchAndResult(
    val id: Int,
    val team1: String,
    val team2: String,
    val team1_points: Int,
    val team2_points: Int,
    val team1_prediction: Int,
    val team2_prediction: Int,
) {
    fun toMatch(): Match {
        return Match(
            id = id,
            team1 = team1,
            team2 = team2,
            team1_points = team1_points,
            team2_points = team2_points,
            team1_prediction = team1_prediction,
            team2_prediction = team2_prediction,
            timestamp = 0
        )
    }
}
