package com.example.bettinapp.presentation.match_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.bettinapp.R
import com.example.bettinapp.domain.model.Match


@Composable
fun MatchDialog(
    match: Match,
    showDialog: Boolean = false,
    onDismiss: () -> Unit,
    onConfirm: (match: Match) -> Unit
) {

    var dialogOpen by remember {
        mutableStateOf(showDialog)
    }


    if (dialogOpen) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality,
                // simply leave this block empty.
                dialogOpen = false
                onDismiss.invoke()
            },
            confirmButton = {
                TextButton(onClick = {
                    // perform the confirm action
                    dialogOpen = false
                    match.apply {
                        if (team1_prediction == null) team1_prediction = 0
                        if (team2_prediction == null) team2_prediction = 0
                    }
                    onConfirm.invoke(match)
                }) {
                    Text(text = stringResource(R.string.button_text_confirm))
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = match.team1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ScoreItem(
                        score = match.team1_prediction ?: 0,
                        onScoreChanged = {
                            match.team1_prediction = it
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ScoreItem(
                        score = match.team2_prediction ?: 0,
                        onScoreChanged = {
                            match.team2_prediction = it
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = match.team2
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(5.dp),
            backgroundColor = MaterialTheme.colors.surface,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }
}