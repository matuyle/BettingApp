package com.example.bettinapp.presentation.match_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettinapp.core.util.Constants
import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.domain.repository.DataStoreRepository
import com.example.bettinapp.domain.use_case.MatchesUseCases
import com.example.bettinapp.presentation.common.Constants.MATCH_RESULTS_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchListViewModel @Inject constructor(
    private val getMatchesUseCases: MatchesUseCases,
    private val datastoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = mutableStateOf(MatchListState())
    val state: State<MatchListState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getMatches() {
        viewModelScope.launch() {
            getMatchesUseCases.getMatchesUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.isNotEmpty() == true) {
                            _state.value = state.value.copy(
                                matches = result.data,
                                isLoading = false
                            )
                        } else {
                            _state.value = MatchListState(
                                matches = emptyList(),
                                isLoading = false,
                                error = result.message ?: "Can't find matches"
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.value = MatchListState(
                            matches = emptyList(),
                            isLoading = false,
                            error = result.message ?: "An unexpected error occured"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: MatchListEvent) {
        when (event) {
            is MatchListEvent.OpenDialog -> {
                viewModelScope.launch() {
                    getMatchesUseCases.getMatchUseCase(event.matchId).also { match ->
                        _state.value = state.value.copy(
                            recentMatch = match,
                            isDialogOpen = true
                        )
                    }
                }

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
                viewModelScope.launch {
                    getMatchesUseCases.addMatchUseCase(event.match)
                }
            }
            is MatchListEvent.OnTopButtonPressed -> {
                viewModelScope.launch {
                    getMatchesUseCases.getMatchWithPrediction().also { match ->
                        match?.let {
                            _eventFlow.emit(UiEvent.MoveToResults)
                            return@launch
                        }
                        _eventFlow.emit(UiEvent.ShowToast("At least one prediction must be made"))
                    }
                }
            }
            is MatchListEvent.OnNav -> {
                viewModelScope.launch(Dispatchers.IO) { storeLatestScreen(MATCH_RESULTS_SCREEN) }
            }
        }
    }

    private suspend fun storeLatestScreen(value: String) {
        datastoreRepository.putString(Constants.PREFERENCES_LATEST_SCREEN, value)
    }

    sealed class UiEvent {
        object MoveToResults : UiEvent()
        data class ShowToast(val message: String) : UiEvent()
    }
}