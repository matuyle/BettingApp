package com.example.bettinapp.domain.repository

import com.example.bettinapp.data.remote.dto.MatchesTdo

interface BettingRepository {

    suspend fun getMatches(): MatchesTdo

}