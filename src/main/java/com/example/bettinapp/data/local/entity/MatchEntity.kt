package com.example.bettinapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bettinapp.domain.model.Match

@Entity(tableName = "matches_table")
data class MatchEntity(
    val team1: String,
    val team2: String,
    val team1_points: Int? = null,
    val team2_points: Int? = null,
    val team1_prediction: Int? = null,
    val team2_prediction: Int? = null,
    val timestamp: Long,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
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
            timestamp = timestamp
        )
    }
}

