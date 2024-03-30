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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun TechnoFlash(
    colors: List<Color>,
){
    var currentColor by remember { mutableStateOf(colors.random()) }
    LaunchedEffect(Unit) {
        while (true) {
            val interval = Random.nextLong(10, 70) // Random interval between 100 to 500 milliseconds
            delay(interval)

            val nextColor = colors.random()

            // Optionally, randomly shift the alpha value
            val newColor = if (Random.nextBoolean()) {
                nextColor.copy(alpha = Random.nextFloat())
            } else {
                nextColor
            }

            currentColor = newColor
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(currentColor)
    )
}