package com.example.bettinapp.domain.repository

import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.domain.model.Match
import com.example.bettinapp.domain.model.MatchAndResult
import com.example.bettinapp.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeBettingRepository : BettingRepository {

    private val matchItems = mutableListOf<Match>()
    private val resultItems = mutableListOf<Result>()
    private val matchResultItems = mutableListOf<MatchAndResult>()
    private val matchItem = Match(0, "team1", "team2", timestamp = 0)
    private val flowMatchItem: Flow<Resource<List<Match>>> = flowOf(Resource.Success(matchItems))
    private val flowResultItem: Flow<Resource<List<Result>>> = flowOf(Resource.Success(resultItems))

    override suspend fun getMatches(time: Long): Flow<Resource<List<Match>>> {
        return flowMatchItem
    }

    override suspend fun getMatchById(id: Int): Match {
        return matchItem
    }

    override suspend fun insertMatch(match: Match) {
        matchItems.add(match)
    }

    override suspend fun getMatchWithPrediction(): Match {
        return matchItem
    }

    override suspend fun getResults(time: Long): Flow<Resource<List<Result>>> {
        return flowResultItem
    }

    override fun getMatchAndResult(time: Long): List<MatchAndResult> {
        return matchResultItems
    }

    override suspend fun deleteTables() {

    }

}