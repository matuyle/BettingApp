package com.example.bettinapp.domain.use_case

import com.example.bettinapp.core.util.Constants.TIMEOUT_LENGTH
import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.domain.model.Match
import com.example.bettinapp.domain.repository.BettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMatchesUseCase @Inject constructor(
    private val repository: BettingRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Match>>> {
        val time = System.currentTimeMillis() - TIMEOUT_LENGTH
        return repository.getMatches(time)
    }
}