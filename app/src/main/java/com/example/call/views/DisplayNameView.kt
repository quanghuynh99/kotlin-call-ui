package com.example.call.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DisplayNameView(
    name: String,
    isMinimized: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        style = if (isMinimized) MaterialTheme.typography.labelMedium else MaterialTheme.typography.bodyMedium,
        color = Color.White,
        modifier = modifier.padding(top = 8.dp)
    )
}