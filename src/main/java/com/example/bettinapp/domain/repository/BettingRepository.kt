package com.example.bettinapp.domain.repository

import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.domain.model.Match
import com.example.bettinapp.domain.model.MatchAndResult
import com.example.bettinapp.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface BettingRepository {

    suspend fun getMatches(time: Long): Flow<Resource<List<Match>>>

    suspend fun getMatchById(id: Int): Match?

    suspend fun insertMatch(match: Match)

    suspend fun getMatchWithPrediction(): Match?

    suspend fun getResults(time: Long): Flow<Resource<List<Result>>>

    fun getMatchAndResult(time: Long): List<MatchAndResult>

    suspend fun deleteTables()

}