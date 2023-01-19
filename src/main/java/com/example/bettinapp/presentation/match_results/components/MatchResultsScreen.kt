package com.example.bettinapp.presentation.match_results.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bettinapp.presentation.Screen
import com.example.bettinapp.presentation.common.components.MatchesList
import com.example.bettinapp.presentation.common.components.TopButton
import com.example.bettinapp.presentation.match_results.MatchResultsEvent
import com.example.bettinapp.presentation.match_results.MatchResultsViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MatchResultsScreen(
    navController: NavController,
    viewModel: MatchResultsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(true) {
        viewModel.getResults()
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is MatchResultsViewModel.UiEvent.OnReset -> {
                    viewModel.onEvent(MatchResultsEvent.OnNav)
                    if (navController.currentBackStack.value.size > 2)
                        navController.navigateUp() else navController.navigate(
                        Screen.MatchListScreen.route
                    )
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
    ) {
        Column(modifier = Modifier.matchParentSize()) {
            TopButton(
                "Restart"
            ) {
                viewModel.onEvent(MatchResultsEvent.OnTopButtonPressed)
                true
            }
            Spacer(modifier = Modifier.height(8.dp))
            MatchesList(
                matches = state.matches.map { it }
            )
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
    BackHandler(onBack = {

    })
}