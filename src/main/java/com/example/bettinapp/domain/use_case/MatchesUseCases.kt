package com.example.bettinapp.domain.use_case

data class MatchesUseCases(
    val getMatchesUseCase: GetMatchesUseCase,
    val getMatchUseCase: GetMatchUseCase,
    val addMatchUseCase: AddMatchUseCase,
    val getMatchWithPrediction: GetMatchWithPrediction,
    val getResultsUseCase: GetResultsUseCase,
    val getMatchesAndResults: GetMatchesAndResults,
    val deleteTablesUseCase: DeleteTablesUseCase,
)
