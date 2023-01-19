package com.example.bettinapp.data.remote.dto

import com.example.bettinapp.data.local.entity.MatchEntity
import com.example.bettinapp.data.local.entity.ResultEntity
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
    fun toMatchEntity(timestamp: Long): MatchEntity {
        return MatchEntity(
            team1 = team1,
            team2 = team2,
            timestamp = timestamp
        )
    }

    fun toResultEntity(timestamp: Long): ResultEntity {
        return ResultEntity(
            team1 = team1,
            team2 = team2,
            team1_points = team1_points,
            team2_points = team2_points,
            timestamp = timestamp
        )
    }
}
