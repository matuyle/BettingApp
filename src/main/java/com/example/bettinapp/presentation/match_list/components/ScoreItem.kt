package com.example.bettinapp.presentation.match_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bettinapp.R

@Composable
fun ScoreItem(
    score: Int,
    onScoreChanged: (Int) -> Unit
) {

    var currentScore by remember {
        mutableStateOf(score)
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                if (score > 0) currentScore--
                onScoreChanged.invoke(currentScore)
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_remove),
                contentDescription = "Remove"
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = currentScore.toString(),
            fontSize = MaterialTheme.typography.h6.fontSize
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = {
                currentScore++
                onScoreChanged.invoke(currentScore)
            },
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "Add")
        }
    }
}

@Preview
@Composable
fun ScoreItemPreview() {
    Surface {
        ScoreItem(
            score = 0,
            onScoreChanged = {}
        )
    }
}