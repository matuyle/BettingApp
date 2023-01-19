package com.example.bettinapp.domain.use_case

import com.example.bettinapp.domain.repository.BettingRepository

class DeleteTablesUseCase(
    private val repository: BettingRepository
) {
    suspend operator fun invoke()  {
       repository.deleteTables()
    }
}