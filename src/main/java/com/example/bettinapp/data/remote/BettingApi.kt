package com.example.bettinapp.data.remote

import com.example.bettinapp.data.remote.dto.MatchesTdo
import retrofit2.http.GET

interface BettingApi {

    //    @GET("/v3/dc441de9-1dc5-462c-992b-6867ddbe2367") // Empty list

    @GET("/v3/172b8b52-63e7-4a3b-b17b-dfef87a0fec8")
    suspend fun getMatches(): MatchesTdo

    @GET("/v3/b050f352-ffd0-4e7a-8820-df1f2ea80df5")
    suspend fun getMatchResults(): MatchesTdo

}