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
import androidx.wear.compose.material.InlineSlider
import androidx.wear.compose.material.InlineSliderDefaults
import androidx.wear.compose.material.OutlinedButton
import androidx.wear.compose.material.Text


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MonoColor(
    navController: NavHostController,
    color: MutableState<Color>,
    brightness: MutableFloatState,
    interval: MutableFloatState,
) {
    val listState = rememberScalingLazyListState(
        initialCenterItemIndex = 0
    )
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        autoCentering = AutoCenteringParams(itemIndex = 0),
        flingBehavior = ScalingLazyColumnDefaults.snapFlingBehavior(
            state = listState,
            snapOffset = 20.dp
        ),
        state = listState
    ) {

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
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = currentColor
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
            InlineSlider(
                value = brightness.floatValue,
                onValueChange = {brightness.floatValue = it},
                steps = 5,
                decreaseIcon = {
                    Icon(imageVector = InlineSliderDefaults.Decrease, contentDescription = "Decrease") },
                increaseIcon = {
                    Icon(imageVector = InlineSliderDefaults.Increase, contentDescription = "Increase")
                },
                valueRange = 0f..1f,
                colors = InlineSliderDefaults.colors(
                    backgroundColor = Color.Black,
                    unselectedBarColor = Color.LightGray,
                    selectedBarColor = Color.Red,
                ),
                segmented = false
            )
        }
        item {
            Text(
                text = "Interval",
                color = Color.White,
            )
        }
        item {
            InlineSlider(
                value = interval.floatValue,
                onValueChange = {interval.floatValue = it},
                steps = 10,
                decreaseIcon = {
                    Icon(imageVector = InlineSliderDefaults.Decrease, contentDescription = "Decrease") },
                increaseIcon = {
                    Icon(imageVector = InlineSliderDefaults.Increase, contentDescription = "Increase")
                },
                valueRange = 0.05f..0.5f,
                colors = InlineSliderDefaults.colors(
                    backgroundColor = Color.Black,
                    unselectedBarColor = Color.LightGray,
                    selectedBarColor = Color.Red,
                ),
                segmented = false
            )
        }
        item {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red
                ),
                onClick = {
                navController.navigate("flash")
            }) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.LightGray
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
