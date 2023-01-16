package com.example.bettinapp.presentation.match_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bettinapp.presentation.common.components.ButtonComposable
import com.example.bettinapp.presentation.common.components.MatchListItem
import com.example.bettinapp.presentation.match_list.MatchListDialogEvent
import com.example.bettinapp.presentation.match_list.MatchListViewModel

@Composable
fun MatchListScreen(
    navController: NavController,
    viewModel: MatchListViewModel = hiltViewModel()
) {
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
                    onDismiss = { viewModel.onEvent(MatchListDialogEvent.DismissDialog) },
                    onConfirm = { match -> viewModel.onEvent(MatchListDialogEvent.ConfirmDialog(match)) }
                )
            }
        }
        Column(modifier = Modifier.matchParentSize()) {
            ButtonComposable("Get results >>")
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.matches) { match ->
                    MatchListItem(
                        match = match,
                        onItemClick = {
                            viewModel.onEvent(MatchListDialogEvent.OpenDialog(match))
                        }
                    )
                }
            }
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

