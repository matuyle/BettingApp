package com.example.bettinapp.data.repository

import android.util.Log
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

    override suspend fun getMatches(): Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading())

        matchDao.getMatches().collect() {
            val matches = it.map { obj -> obj.toMatch() }
            if (matches.isNotEmpty()) {
                emit(Resource.Success(matches))
                Log.d("LogB", matches.size.toString())
                return@collect
            }

            try {
                val remoteMatches = api.getMatches().matches
                matchDao.insertMatches(remoteMatches.map { matchDto -> matchDto.toMatchEntity() })
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

    override suspend fun getResults(): Flow<Resource<List<Result>>> = flow {
        resultDao.getResults().collect() {
            val results = it.map { obj -> obj.toResult() }
            if (results.isNotEmpty()) {
                emit(Resource.Success(results))
                return@collect
            }

            try {
                val remoteMatches = api.getMatchResults().matches
                resultDao.insertResults(remoteMatches.map { matchDto -> matchDto.toResultEntity() })
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

    override fun getMatchAndResult(): List<MatchAndResult> {
        return matchDao.getMatchesAndResults()
    }

    override suspend fun deleteTables() {
        matchDao.clearMatches()
        resultDao.clearResults()
    }

}