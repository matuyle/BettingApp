package com.example.bettinapp.domain.use_case

import com.example.bettinapp.domain.model.Match
import com.example.bettinapp.domain.repository.BettingRepository

class AddMatchUseCase(
    private val repository: BettingRepository
) {
    suspend operator fun invoke(match: Match) {
        repository.insertMatch(match)
    }
}