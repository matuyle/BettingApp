package com.example.bettinapp.domain.repository

import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.domain.model.Match
import com.example.bettinapp.domain.model.MatchAndResult
import com.example.bettinapp.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface BettingRepository {

    suspend fun getMatches(): Flow<Resource<List<Match>>>

    suspend fun getMatchById(id: Int): Match?

    suspend fun insertMatch(match: Match)

    suspend fun getMatchWithPrediction(): Match?

    suspend fun getResults(): Flow<Resource<List<Result>>>

    fun getMatchAndResult(): List<MatchAndResult>

    suspend fun deleteTables()


}