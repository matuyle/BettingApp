package com.example.bettinapp.presentation.match_list.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bettinapp.presentation.Screen
import com.example.bettinapp.presentation.common.components.MatchesList
import com.example.bettinapp.presentation.common.components.TopButton
import com.example.bettinapp.presentation.match_list.MatchListEvent
import com.example.bettinapp.presentation.match_list.MatchListViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MatchListScreen(
    navController: NavController,
    viewModel: MatchListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.getMatches()
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is MatchListViewModel.UiEvent.MoveToResults -> {
                    navController.navigate(Screen.MatchResultScreen.route)
                }
                is MatchListViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
    ) {
        if (state.isDialogOpen) {
            state.recentMatch?.let {
                MatchDialog(
                    match = it,
                    showDialog = true,
                    onDismiss = { viewModel.onEvent(MatchListEvent.DismissDialog) },
                    onConfirm = { match ->
                        viewModel.onEvent(
                            MatchListEvent.ConfirmDialog(
                                match
                            )
                        )
                    }
                )
            }
        }
        Column(modifier = Modifier.matchParentSize()) {
            TopButton(
                "Get results >>",
            ) {
                viewModel.onEvent(MatchListEvent.OnTopButtonPressed)
                true
            } // Todo give callback
            Spacer(modifier = Modifier.height(8.dp))
            MatchesList(
                matches = state.matches,
                onItemClick = { id ->
                    viewModel.onEvent(MatchListEvent.OpenDialog(id))
                })
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
}

