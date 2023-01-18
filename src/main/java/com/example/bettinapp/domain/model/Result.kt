package com.example.bettinapp.domain.model

data class Result(
    val id: Int? = null,
    val team1: String,
    val team2: String,
    var team1_points: Int? = null,
    var team2_points: Int? = null,
    val timestamp: Long
)
