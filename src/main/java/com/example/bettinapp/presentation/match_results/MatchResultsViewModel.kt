package com.example.bettinapp.presentation.match_results

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettinapp.R
import com.example.bettinapp.core.util.Constants
import com.example.bettinapp.core.util.Resource
import com.example.bettinapp.domain.model.Result
import com.example.bettinapp.domain.repository.DataStoreRepository
import com.example.bettinapp.domain.use_case.MatchesUseCases
import com.example.bettinapp.presentation.common.Constants.MATCH_LIST_SCREEN
import com.example.bettinapp.presentation.match_results.ScreenState.RESET
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchResultsViewModel @Inject constructor(
    private val getMatchesUseCases: MatchesUseCases,
    private val datastoreRepository: DataStoreRepository,
    private val application: Application
) : ViewModel() {

    private val _state = mutableStateOf(MatchResultsState())
    val state: State<MatchResultsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getResults() {
        viewModelScope.launch(Dispatchers.IO) {
            getMatchesUseCases.getResultsUseCase().onEach { result ->
                handleResult(result)
            }.launchIn(this)
        }
    }

    fun handleResult(result: Resource<List<Result>>) {
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
                            error = application.applicationContext
                                .getString(R.string.match_result_screen_text_no_results)
                        )
                    }
                }
            }
            is Resource.Error -> {
                _state.value = MatchResultsState(
                    matches = emptyList(),
                    error = result.message
                        ?: application.applicationContext.getString(R.string.text_error_1)
                )
            }
            is Resource.Loading -> {
                _state.value = MatchResultsState(isLoading = true)
            }
        }
    }

    fun onEvent(event: MatchResultsEvent) {
        when (event) {
            is MatchResultsEvent.OnTopButtonPressed -> {
                _state.value = MatchResultsState(isLoading = true, state = RESET)
                sendUiEvent(UiEvent.OnReset)

            }
            is MatchResultsEvent.OnNav -> {
                viewModelScope.launch {
                    storeLatestScreen(MATCH_LIST_SCREEN)
                }
            }
        }
    }

    fun sendUiEvent(event: UiEvent): Boolean {
        viewModelScope.launch {
            getMatchesUseCases.deleteTablesUseCase()
            _eventFlow.emit(event)
        }
        return true
    }

    suspend fun storeLatestScreen(value: String) {
        datastoreRepository.putString(
            Constants.PREFERENCES_LATEST_SCREEN,
            value
        )
    }

    sealed class UiEvent {
        object OnReset : UiEvent()
    }
}