package com.example.bettinapp.data.repository

import com.example.bettinapp.data.remote.BettingApi
import com.example.bettinapp.data.remote.dto.MatchesTdo
import com.example.bettinapp.domain.repository.BettingRepository
import javax.inject.Inject

class BettingRepositoryImpl @Inject constructor(
    private val api: BettingApi
) : BettingRepository {

    override suspend fun getMatches(): MatchesTdo {
        return api.getMatches()
    }

}