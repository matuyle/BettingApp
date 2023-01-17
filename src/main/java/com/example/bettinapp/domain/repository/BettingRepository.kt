package com.example.bettinapp.domain.repository

import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.data.remote.dto.MatchesTdo
import com.example.bettinapp.domain.model.Match
import kotlinx.coroutines.flow.Flow

interface BettingRepository {

    fun getMatches(): Flow<Resource<List<Match>>>

    suspend fun insertMatch(match: Match)

    suspend fun getMatchResults(): MatchesTdo

}