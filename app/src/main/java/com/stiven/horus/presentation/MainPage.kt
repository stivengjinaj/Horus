package com.stiven.horus.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text

@Composable
fun MainPage(
    navController: NavHostController
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier.width((screenWidth-50).dp),
            onClick = {
                navController.navigate("mono-color")
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            )
        ) {
            Text(
                text = "Mono-color",
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier.width((screenWidth-50).dp),
            onClick = {
                navController.navigate("modes")
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            )
        ) {
            Text(
                text = "Modes",
                color = Color.Black
            )
        }
    }
}