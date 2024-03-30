package com.stiven.horus.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.wear.compose.material.Icon
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyColumnDefaults
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.OutlinedButton
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MonoColor(
    navController: NavHostController,
    color: MutableState<Color>,
    brightness: MutableFloatState,
    interval: MutableFloatState,
) {
    val listState = rememberScalingLazyListState(
        initialCenterItemIndex = 1
    )
    TimeText()
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        autoCentering = AutoCenteringParams(itemIndex = 0),
        flingBehavior = ScalingLazyColumnDefaults.snapFlingBehavior(
            state = listState,
            snapOffset = 10.dp
        ),
        state = listState
    ) {

        item {
            Spacer(modifier = Modifier.height(5.dp))
        }
        item {
            FlowRow (
                modifier = Modifier.clip(RoundedCornerShape(10,10)),
                maxItemsInEachRow = 5,
                horizontalArrangement = Arrangement.Center,
            ){
                for(currentColor in Colors.gradientColors){
                    OutlinedButton(
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                            .clip(RoundedCornerShape(50)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = currentColor
                        ),
                        border = ButtonDefaults.buttonBorder(
                            borderStroke = BorderStroke(
                                3.dp,
                                if(color.value == currentColor)
                                    if(currentColor == Color.Red)
                                        Color.White
                                    else Color.Red
                                else currentColor
                            )
                        ),
                        onClick = {
                            color.value = currentColor
                        }
                    ) {

                    }
                    Spacer(modifier = Modifier.width(5.dp))
                }
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
            Button(onClick = {
                navController.navigate("flash")
            }) {
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



object Colors {
    val gradientColors = listOf(
        Color(0xffffffff),
        Color(0xffffff00),
        Color(0xff00ff00),
        Color(0xff0000ff),
        Color(0xffff00ff),
        Color(0xffff0000)
    )
}
