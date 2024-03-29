package com.stiven.technolight.presentation

import android.view.MotionEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyColumnDefaults
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.TimeText
import kotlin.math.roundToInt

private const val thumbRadius = 20f

@Composable
fun MonoColor(
    navController: NavHostController,
    color: MutableState<Color>,
    brightness: MutableFloatState,
    interval: MutableFloatState) {
    val listState = rememberScalingLazyListState(
        initialCenterItemIndex = 3
    )
    val whiteChosen = remember {
        mutableStateOf(color.value == Color.White)
    }
    TimeText()
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        autoCentering = AutoCenteringParams(itemIndex = 0),
        flingBehavior = ScalingLazyColumnDefaults.snapFlingBehavior(
            state = listState,
            snapOffset = 10.dp
            // Exponential decay by default. You can also explicitly define a
            // DecayAnimationSpec.
        ),
        state = listState
    ) {
        item {
            Text(
                text = "Color",
                color = Color.White,
            )
        }
        item {
            Spacer(modifier = Modifier.height(5.dp))
        }
        item {
            ColorPicker(color)
        }
        item {
            OutlinedButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .width(30.dp)
                    .height(30.dp),
                border = BorderStroke(2.dp, if(whiteChosen.value) Color.Red else Color.White),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.White
                ),
                onClick = {
                    color.value = Color.White
                    whiteChosen.value = !whiteChosen.value
                }
            ) {

            }
        }
        item {
            Spacer(modifier = Modifier.height(5.dp))
        }
        item {
            Text(
                text = "Brightness",
                color = Color.White,
            )
        }
        item {
            Slider(
                value = brightness.floatValue,
                onValueChange = { brightness.floatValue = it },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.Red,
                    inactiveTrackColor = Color.Gray,
                ),
                steps = 3,
                valueRange = 0f..1f
            )
        }
        item {
            Text(
                text = "Interval",
                color = Color.White,
            )
        }
        item {
            Slider (
                value = interval.floatValue,
                onValueChange = { interval.floatValue = it },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.Gray,
                ),
                steps = 10,
                valueRange = 0.05f..0.7f
            )
        }
        item {
            IconButton(
                modifier = Modifier.size(70.dp),
                onClick = {
                    navController.navigate("flash")
                }
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColorPicker(rangeColor: MutableState<Color>) {

    ColorSlideBar(colors = Colors.gradientColors) {
        val (rangeProgress, range) = ColorPickerHelper.calculateRangeProgress(it.toDouble())
        val red: Int
        val green: Int
        val blue: Int
        when (range) {
            ColorRange.RedToYellow -> {
                red = 255
                green = (255 * rangeProgress).roundToInt()
                blue = 0
            }

            ColorRange.YellowToGreen -> {
                red = (255 * (1 - rangeProgress)).roundToInt()
                green = 255
                blue = 0
            }

            ColorRange.GreenToCyan -> {
                red = 0
                green = 255
                blue = (255 * rangeProgress).roundToInt()
            }

            ColorRange.CyanToBlue -> {
                red = 0
                green = (255 * (1 - rangeProgress)).roundToInt()
                blue = 255
            }

            ColorRange.BlueToPurple -> {
                red = (255 * rangeProgress).roundToInt()
                green = 0
                blue = 255
            }
            ColorRange.PurpleToRed -> {
                red = 255
                green = 0
                blue = (255 * (1 - rangeProgress)).roundToInt()
            }
        }
        rangeColor.value = Color(red, green, blue)
    }
}

@ExperimentalComposeUiApi
@Composable
fun ColorSlideBar(colors: List<Color>, onProgress: (Float) -> Unit) {
    var progress by remember {
        mutableFloatStateOf(1f)
    }
    var slideBarSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    LaunchedEffect(progress) {
        onProgress(progress)
    }
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(22.dp)
            .onSizeChanged {
                slideBarSize = it
            }
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                        progress = (it.x / slideBarSize.width).coerceIn(0f, 1f)
                    }
                }
                return@pointerInteropFilter true
            }
            .clipToBounds()
            .clip(RoundedCornerShape(100))
            .border(0.2.dp, Color.LightGray, RoundedCornerShape(100))
    ) {
        drawTransparentBackground(3)
        drawRect(Brush.horizontalGradient(colors, startX = size.height/2, endX = size.width - size.height/2))
        drawCircle(
            Color.White,
            radius = thumbRadius,
            center = Offset(
                thumbRadius + (size.height / 2 - thumbRadius) + ((size.width - (thumbRadius + (size.height / 2 - thumbRadius)) * 2) * progress),
                size.height / 2
            )
        )
    }
}


enum class ColorRange {
    RedToYellow,
    YellowToGreen,
    GreenToCyan,
    CyanToBlue,
    BlueToPurple,
    PurpleToRed
}

object Colors {
    val gradientColors = listOf(
        Color(0xffff0000),
        Color(0xffffff00),
        Color(0xff00ff00),
        Color(0xff00ffff),
        Color(0xff0000ff),
        Color(0xffff00ff),
        Color(0xffff0000)
    )
}

fun DrawScope.drawTransparentBackground(verticalBoxesSize: Int = 10) {
    val boxSize = size.height / verticalBoxesSize
    repeat((size.width / boxSize).roundToInt() + 1) { x ->
        repeat(verticalBoxesSize) { y ->
            drawRect(
                if ((y + x) % 2 == 0) {
                    Color.LightGray
                } else {
                    Color.White
                }, topLeft = Offset(x * boxSize, y * boxSize), size = Size(boxSize, boxSize)
            )
        }
    }
}
