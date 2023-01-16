package com.example.bettinapp.data.remote

import com.example.bettinapp.data.remote.dto.MatchesTdo
import retrofit2.http.GET

interface BettingApi {

    @GET("/v3/172b8b52-63e7-4a3b-b17b-dfef87a0fec8")
    suspend fun getMatches(): MatchesTdo

}