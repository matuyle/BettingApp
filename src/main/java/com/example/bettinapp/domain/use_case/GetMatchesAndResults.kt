package com.example.bettinapp.domain.use_case

import com.example.bettinapp.domain.model.MatchAndResult
import com.example.bettinapp.domain.repository.BettingRepository
import javax.inject.Inject

class GetMatchesAndResults @Inject constructor(
    private val repository: BettingRepository
) {
    operator fun invoke(): List<MatchAndResult> {
        return repository.getMatchAndResult()
    }
}