package com.example.bettinapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bettinapp.core.util.Constants.PREFERENCES_LATEST_SCREEN
import com.example.bettinapp.domain.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val datastoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch { getLatestScreen() }
    }

    private suspend fun getLatestScreen() {
        viewModelScope.launch {
            datastoreRepository.getString(PREFERENCES_LATEST_SCREEN).also { screen ->
                _state.value = state.value.copy(
                    isPreferencesLoaded = true,
                    screen = screen
                )
            }

        }
    }

    sealed class UiEvent {
        object RedirectToResultsScreen : UiEvent()
    }
}