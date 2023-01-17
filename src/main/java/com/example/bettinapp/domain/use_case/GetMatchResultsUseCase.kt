package com.example.bettinapp.domain.use_case

import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.data.remote.dto.toMatches
import com.example.bettinapp.domain.model.Match
import com.example.bettinapp.domain.repository.BettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMatchResultsUseCase @Inject constructor(
    private val repository: BettingRepository
) {
    operator fun invoke(): Flow<Resource<List<Match>>> = flow {
        try {
            emit(Resource.Loading<List<Match>>())
            val matches = repository.getMatchResults().toMatches().matches
            emit(Resource.Success<List<Match>>(matches))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Match>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Match>>("Couldn't reach server. Check your internet connection."))
        }
    }
}