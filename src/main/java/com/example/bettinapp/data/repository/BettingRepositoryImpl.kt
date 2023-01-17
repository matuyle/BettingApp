package com.example.bettinapp.data.repository

import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.data.local.MatchDao
import com.example.bettinapp.data.remote.BettingApi
import com.example.bettinapp.data.remote.dto.MatchesTdo
import com.example.bettinapp.domain.model.Match
import com.example.bettinapp.domain.repository.BettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BettingRepositoryImpl @Inject constructor(
    private val api: BettingApi,
    private val dao: MatchDao
) : BettingRepository {

    override fun getMatches(): Flow<Resource<List<Match>>> = flow {
        emit(Resource.Loading())

        val matches = dao.getMatches().map { it.toMatch() }
        if (matches.isNotEmpty()) {
            emit(Resource.Success(matches))
            return@flow
        }

        try {
            val remoteMatches = api.getMatches().matches
            dao.clearAll()
            dao.insertMatches(remoteMatches.map { it.toMatchEntity() })
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
        val newMatches = dao.getMatches().map { it.toMatch() }
        emit(Resource.Success(newMatches))
    }

    override suspend fun insertMatch(match: Match) {
        TODO("Not yet implemented")
    }

    override suspend fun getMatchResults(): MatchesTdo {
        return api.getMatchResults()
    }

}