package com.example.bettinapp.presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bettinapp.domain.model.Match

@Composable
fun MatchesList(
    matches: List<Match>,
    onItemClick: (match: Match) -> Unit? = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(matches) { match ->
            MatchListItem(
                match = match,
                onItemClick = {
                    onItemClick.invoke(match)
                }
            )
        }
    }
}