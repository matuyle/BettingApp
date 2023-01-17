package com.example.bettinapp.presentation.match_results

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.domain.use_case.GetMatchResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MatchResultsViewModel @Inject constructor(
    private val getMatchResultsUseCase: GetMatchResultsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MatchResultsState())
    val state: State<MatchResultsState> = _state

    init {
        getMatchResults()
    }

    private fun getMatchResults() {
        getMatchResultsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MatchResultsState(matches = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = MatchResultsState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = MatchResultsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}