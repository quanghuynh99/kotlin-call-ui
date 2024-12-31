package com.example.call.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ContentView(
    state: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = state,
        style = MaterialTheme.typography.labelSmall,
        color = Color.White,
        modifier = modifier.padding(top = 4.dp)
    )
}