package com.example.bettinapp.domain.model

data class Match(
    val team1: String,
    val team2: String,
    var team_points1: Int? = null,
    var team_points2: Int? = null,
)