package com.example.call.enums

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class ProfileViewSize(val modifier: Modifier) {
    MINIMIZE(Modifier.size(112.dp, 156.dp)),
    FULLSCREEN(Modifier.fillMaxSize())
}