package com.example.bettinapp.domain.use_case

import com.example.bettinapp.domain.model.Match
import com.example.bettinapp.domain.repository.BettingRepository
import javax.inject.Inject

class GetMatchUseCase @Inject constructor(
    private val repository: BettingRepository
) {
    suspend operator fun invoke(id: Int): Match? {
        return repository.getMatchById(id)
    }
}