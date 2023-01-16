package com.example.bettinapp.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bettinapp.domain.model.Match

@Composable
fun MatchListItem(
    match: Match,
    onItemClick: (Match) -> Unit
) {
    Card(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .clickable { onItemClick(match) },
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier.weight(5f),
                text = match.team1,
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${match.team_points1 ?: "_"}",
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.width(8.dp),
                text = "-",
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${match.team_points2 ?: "_"}",
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.weight(5f),
                text = match.team2,
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview
@Composable
fun MatchListItemPreview() {
    Surface() {
        MatchListItem(
            match = Match(
                team1 = "Team1",
                team2 = "Team2",
                team_points1 = 2,
                team_points2 = 3
            ), onItemClick = {})
    }
}