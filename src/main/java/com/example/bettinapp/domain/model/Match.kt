package com.example.bettinapp.domain.model

import com.example.bettinapp.data.local.entity.MatchEntity

data class Match(
    val id: Int? = null,
    val team1: String,
    val team2: String,
    var team1_points: Int? = null,
    var team2_points: Int? = null,
    var team1_prediction: Int? = null,
    var team2_prediction: Int? = null,
    val timestamp: Long
) {
    fun toMatchEntity(): MatchEntity {
        return MatchEntity(
            id = id,
            team1 = team1,
            team2 = team2,
            team1_points = team1_points,
            team2_points = team2_points,
            team1_prediction = team1_prediction,
            team2_prediction = team2_prediction,
            timestamp = timestamp
        )
    }
}