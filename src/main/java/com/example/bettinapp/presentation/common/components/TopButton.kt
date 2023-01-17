package com.example.bettinapp.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TopButton(
    text: String,
    onButtonPressed: () -> Boolean = { false }
) {
    Column(
        modifier = Modifier
            .background(Color.LightGray),
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
        TopButton("Get results >>")
    }
}