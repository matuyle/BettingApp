package com.example.bettinapp.domain.use_case

import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.domain.model.Result
import com.example.bettinapp.domain.repository.BettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetResultsUseCase @Inject constructor(
    private val repository: BettingRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Result>>> {
        return repository.getResults()
    }
}