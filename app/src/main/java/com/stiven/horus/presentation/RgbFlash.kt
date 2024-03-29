package com.stiven.horus.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun RgbFlash(){
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.White)
    LinearFlash(colors)
}