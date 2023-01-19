package com.example.bettinapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bettinapp.presentation.common.Constants
import com.example.bettinapp.presentation.match_list.components.MatchListScreen
import com.example.bettinapp.presentation.match_results.components.MatchResultsScreen
import com.example.bettinapp.presentation.ui.theme.BettinAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: DataStoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BettinAppTheme {
                val state = viewModel.state.value
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colors.background) {
                    if (state.isPreferencesLoaded) {
                        NavHost(
                            navController = navController,
                            startDestination = if (state.screen.contains(Constants.MATCH_RESULTS_SCREEN))
                                Screen.MatchResultScreen.route else Screen.MatchListScreen.route
                        ) {
                            composable(
                                route = Screen.MatchListScreen.route
                            ) {
                                MatchListScreen(navController)
                            }
                            composable(
                                route = Screen.MatchResultScreen.route
                            ) {
                                MatchResultsScreen(navController)
                            }
                        }
                        LaunchedEffect(true) {

                            viewModel.eventFlow.collectLatest { event ->
                                when (event) {
                                    is DataStoreViewModel.UiEvent.RedirectToResultsScreen -> {
                                        navController.navigate(Screen.MatchResultScreen.route)
                                    }
                                }
                            }
                        }
                    } else {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }
}