package com.stiven.horus.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.foundation.curvedComposable
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.InlineSlider
import androidx.wear.compose.material.InlineSliderDefaults
import androidx.wear.compose.material.Text
import com.stiven.technolight.R

@Composable
fun Modes(
    navController: NavHostController,
    brightness: MutableFloatState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.padding(10.dp, 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Brightness",
                color = Color.White
            )
            InlineSlider(
                value = brightness.floatValue,
                onValueChange = { brightness.floatValue = it },
                steps = 5,
                decreaseIcon = {
                    Icon(imageVector = InlineSliderDefaults.Decrease, contentDescription = "Decrease")
                },
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
        FlashButton(anchor = 160f, icon = R.drawable.linear, description = "linear", onClick = {navController.navigate("linear")})
        FlashButton(anchor = 115f, icon = R.drawable.techno, description = "techno", onClick = {navController.navigate("techno")})
        FlashButton(anchor = 65f, icon = R.drawable.random, description = "random", onClick = {navController.navigate("rgb")})
        FlashButton(anchor = 20f, icon = R.drawable.flashlight, description = "flashlight", onClick = {navController.navigate("flashlight")})
    }
}

@Composable
fun FlashButton(
    anchor: Float,
    icon: Int,
    description: String,
    onClick: () -> Unit
){
    CurvedLayout(
        anchor = anchor,
    ) {
        curvedComposable {
            Button(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp),
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    modifier = Modifier.rotate(-(anchor+90)),
                    painter = painterResource(id = icon),
                    contentDescription = description
                )
            }
        }
    }
}