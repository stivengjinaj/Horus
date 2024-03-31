package com.stiven.horus.presentation

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Flash(
    color: Color,
    interval: Float,
    brightness: Float,
){
    val context = LocalContext.current
    var currentColor by remember { mutableStateOf(color) }
    val newInterval = remember {
        mutableFloatStateOf(interval)
    }
    setBrightness(context, brightness)

    LaunchedEffect(Unit) {
        while (true) {
            delayForFloatValue(newInterval.floatValue)
            currentColor = if (currentColor == color) Color.Black else color
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(currentColor)
            .combinedClickable(
                onClick = {
                    if(newInterval.floatValue >= 0.5f){
                        newInterval.floatValue = 0.05f
                    }else {
                        newInterval.floatValue += 0.05f
                    }
                }
            )
        ,
        contentAlignment = Alignment.Center
    ) {

    }
}

fun setBrightness(context: Context, intensity: Float) {
    val activity = context as? Activity ?: return
    val layoutParams: WindowManager.LayoutParams = activity.window.attributes
    layoutParams.screenBrightness = intensity
    activity.window.attributes = layoutParams
}

suspend fun delayForFloatValue(value: Float) {
    delay((value * 1000).toLong())
}