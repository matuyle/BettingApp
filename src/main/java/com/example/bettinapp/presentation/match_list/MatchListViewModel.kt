package com.example.bettinapp.presentation.match_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.domain.use_case.MatchesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val getMatchesUseCases: MatchesUseCases
) : ViewModel() {

    private val _state = mutableStateOf(MatchListState())
    val state: State<MatchListState> = _state

    private var getListJob: Job? = null

    fun getMatches() {
        getListJob?.cancel()
        getListJob = viewModelScope.launch(Dispatchers.IO) {
            getMatchesUseCases.getMatchesUseCase().onEach { result ->
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
            }.launchIn(this)
        }
    }

    fun onEvent(event: MatchListEvent) {
        when (event) {
            is MatchListEvent.OpenDialog -> {
                _state.value = state.value.copy(
                    recentMatch = event.match,
                    isDialogOpen = true
                )
            }
            is MatchListEvent.DismissDialog -> {
                _state.value = state.value.copy(
                    isDialogOpen = false
                )
            }
            is MatchListEvent.ConfirmDialog -> {
                _state.value = state.value.copy(
                    recentMatch = event.match,
                    isDialogOpen = false
                )
            }
            is MatchListEvent.OnTopButtonPressed -> {

            }
        }
    }

}