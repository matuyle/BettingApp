package com.example.bettinapp.data.repository

import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.data.local.MatchDao
import com.example.bettinapp.data.local.ResultDao
import com.example.bettinapp.data.remote.BettingApi
import com.example.bettinapp.domain.model.Match
import com.example.bettinapp.domain.model.MatchAndResult
import com.example.bettinapp.domain.model.Result
import com.example.bettinapp.domain.repository.BettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BettingRepositoryImpl @Inject constructor(
    private val api: BettingApi,
    private val matchDao: MatchDao,
    private val resultDao: ResultDao
) : BettingRepository {

    override suspend fun getMatches(time: Long): Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading())
        matchDao.getMatches(time).collect {
            val matches = it.map { obj -> obj.toMatch() }
            if (matches.isNotEmpty()) {
                emit(Resource.Success(matches))
                return@collect
            }

            try {
                matchDao.clearMatches()
                val remoteMatches = api.getMatches().matches
                val timestamp = System.currentTimeMillis()
                matchDao.insertMatches(remoteMatches.map { matchDto ->
                    matchDto
                        .toMatchEntity(timestamp)
                })
                if (remoteMatches.isEmpty())
                    emit(Resource.Success(emptyList()))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = matches
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = "Couldn't reach server, check your internet connection.",
                        data = matches
                    )
                )
            }
        }
    }

    override suspend fun getMatchById(id: Int): Match? {
        return matchDao.getMatchById(id)
    }

    override suspend fun insertMatch(match: Match) {
        matchDao.insertMatch(match.toMatchEntity())
    }

    override suspend fun getMatchWithPrediction(): Match? {
        return matchDao.getMatchWithPrediction()
    }

    override suspend fun getResults(time: Long): Flow<Resource<List<Result>>> = flow {
        emit(Resource.Loading())
        resultDao.getResults(time).collect() {
            val results = it.map { obj -> obj.toResult() }
            if (results.isNotEmpty()) {
                emit(Resource.Success(results))
                return@collect
            }

            try {
                resultDao.clearResults()
                val remoteMatches = api.getMatchResults().matches
                val timestamp = System.currentTimeMillis()
                resultDao.insertResults(remoteMatches.map { matchDto ->
                    matchDto
                        .toResultEntity(timestamp)
                })
                if (remoteMatches.isEmpty())
                    emit(Resource.Success(emptyList()))
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = results
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = "Couldn't reach server, check your internet connection.",
                        data = results
                    )
                )
            }
        }
    }

    override fun getMatchAndResult(time: Long): List<MatchAndResult> {
        return matchDao.getMatchesAndResults(time)
    }

    override suspend fun deleteTables() {
        matchDao.clearMatches()
        resultDao.clearResults()
    }

}