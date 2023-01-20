package com.example.bettinapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bettinapp.R

@Composable
fun TopButton(
    text: String,
    onButtonPressed: () -> Boolean = { false }
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    onButtonPressed.invoke()
                }) {
                Text(
                    text = text
                )
            }
        }
    }
}

@Preview
@Composable
fun TestingComposablePreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        TopButton(stringResource(R.string.button_text_get_results))
    }
}