package com.example.bettinapp.presentation.match_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettinapp.core.Resource
import com.example.bettinapp.domain.use_case.get_matches.GetMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MatchListState())
    val state: State<MatchListState> = _state

    init {
        getMatches()
    }

    private fun getMatches() {
        getMatchesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MatchListState(matches = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = MatchListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = MatchListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MatchListDialogEvent) {
        when (event) {
            is MatchListDialogEvent.OpenDialog -> {
                _state.value = state.value.copy(
                    recentMatch = event.match,
                    isDialogOpen = true
                )
            }
            is MatchListDialogEvent.DismissDialog -> {
                _state.value = state.value.copy(
                    isDialogOpen = false
                )
            }
            is MatchListDialogEvent.ConfirmDialog -> {
                _state.value = state.value.copy(
                    recentMatch = event.match,
                    isDialogOpen = false
                )
            }
        }
    }

}