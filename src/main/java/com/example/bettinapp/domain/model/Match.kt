package com.example.bettinapp.domain.model

data class Match(
    val team1: String,
    val team2: String,
    var team1_points: Int? = null,
    var team2_points: Int? = null,
    var team1_prediction: Int? = null,
    var team2_prediction: Int? = null,
    val timestamp: Long
)