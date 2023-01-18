package com.example.bettinapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bettinapp.domain.model.Result

@Entity(tableName = "result_table")
data class ResultEntity(
    val team1: String,
    val team2: String,
    val team1_points: Int?,
    val team2_points: Int?,
    val timestamp: Long,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
) {
    fun toResult(): Result {
        return Result(
            id = id,
            team1 = team1,
            team2 = team2,
            team1_points = team1_points,
            team2_points = team2_points,
            timestamp = timestamp
        )
    }
}