package com.example.bettinapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MatchesTdo(
    @SerializedName("matches")
    val matches: List<MatchDto>
)