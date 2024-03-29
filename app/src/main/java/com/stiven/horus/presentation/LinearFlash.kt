package com.stiven.horus.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun LinearFlash(
    colors: List<Color>
) {
    var currentColor by remember { mutableStateOf(colors.first()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(100)
            currentColor = colors.random()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize().background(currentColor),
        contentAlignment = Alignment.Center
    ) {
        // Display anything you want in the center
    }
}