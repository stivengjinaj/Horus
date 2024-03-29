package com.stiven.horus.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyColumnDefaults
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText

@Composable
fun Modes(
    navController: NavHostController,
    brightness: MutableFloatState
) {
    val context = LocalContext.current
    val listState = rememberScalingLazyListState(
        initialCenterItemScrollOffset = 3
    )
    val screenWidth = LocalConfiguration.current.screenWidthDp
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
    ){
        item {
            Text(
                text = "Brightness",
                color = Color.White
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
            Button(
                modifier = Modifier.width((screenWidth-50).dp),
                onClick = {
                    setBrightness(context = context, brightness.floatValue)
                    navController.navigate("linear")
                },
                colors = androidx.wear.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(
                    text = "Linear",
                    color = Color.Black
                )
            }
        }
        item {
            Button(
                modifier = Modifier.width((screenWidth-50).dp),
                onClick = {
                    setBrightness(context = context, brightness.floatValue)
                    navController.navigate("techno")
                },
                colors = androidx.wear.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(
                    text = "Techno",
                    color = Color.Black
                )
            }
        }
        item {
            Button(
                modifier = Modifier.width((screenWidth-50).dp),
                onClick = {
                    setBrightness(context = context, brightness.floatValue)
                    navController.navigate("rgb")
                },
                colors = androidx.wear.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(
                    text = "Random",
                    color = Color.Black
                )
            }
        }
    }
}