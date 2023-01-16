package com.example.bettinapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bettinapp.presentation.match_list.components.MatchListScreen
import com.example.bettinapp.presentation.ui.theme.BettinAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BettinAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MatchListScreen.route
                    ) {
                        composable(
                            route = Screen.MatchListScreen.route
                        ) {
                            MatchListScreen(navController)
                        }
                    }
                }
            }
        }
    }
}