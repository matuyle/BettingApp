package com.example.bettinapp.presentation.match_results

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettinapp.core.util.Constants
import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.domain.repository.DataStoreRepository
import com.example.bettinapp.domain.use_case.MatchesUseCases
import com.example.bettinapp.presentation.common.Constants.MATCH_LIST_SCREEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchResultsViewModel @Inject constructor(
    private val getMatchesUseCases: MatchesUseCases,
    private val datastoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = mutableStateOf(MatchResultsState())
    val state: State<MatchResultsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getResults() {
        viewModelScope.launch(Dispatchers.IO) {
            getMatchesUseCases.getResultsUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        getMatchesUseCases.getMatchesAndResults().also { list ->
                            if (list.isNotEmpty()) {
                                _state.value = state.value.copy(
                                    matches = list.map { it.toMatch() },
                                    isLoading = false
                                )
                            } else {
                                _state.value = state.value.copy(
                                    matches = emptyList(),
                                    isLoading = false,
                                    error = result.message ?: "Can't find results"
                                )
                            }
                        }
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
            }.launchIn(this)
        }
    }

    fun onEvent(event: MatchResultsEvent) {
        when (event) {
            is MatchResultsEvent.OnTopButtonPressed -> {
                viewModelScope.launch {
                    _state.value = MatchResultsState(isLoading = true)
                    getMatchesUseCases.deleteTablesUseCase().also {
                        delay(300)
                        _eventFlow.emit(UiEvent.OnReset)
                    }
                }
            }
            is MatchResultsEvent.OnNav -> {
                viewModelScope.launch { storeLatestScreen(MATCH_LIST_SCREEN) }
            }
        }
    }

    private suspend fun storeLatestScreen(value: String) {
        datastoreRepository.putString(
            Constants.PREFERENCES_LATEST_SCREEN,
            value
        )
    }

    sealed class UiEvent {
        object OnReset : UiEvent()
    }

}