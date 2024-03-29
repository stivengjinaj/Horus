/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.stiven.horus.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.stiven.horus.presentation.theme.TechnoLightTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {

    val navController = rememberSwipeDismissableNavController()
    val color = remember{
        mutableStateOf(Color.White)
    }
    val brightness = remember {
        mutableFloatStateOf(0.5f)
    }
    val interval = remember {
        mutableFloatStateOf(0.5f)
    }
    val colors = listOf(
        Color.White,
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Magenta,
        Color.Yellow
    )

    TechnoLightTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            TimeText()
            SwipeDismissableNavHost(
                navController = navController,
                startDestination = "root"
            ) {
                composable("root"){
                    MainPage(
                        navController = navController
                    )
                }
                composable("mono-color"){
                    MonoColor(
                        navController = navController,
                        color = color,
                        brightness = brightness,
                        interval = interval
                    )
                }
                composable("modes"){
                    Modes(
                        navController = navController,
                        brightness = brightness
                    )
                }
                composable("flash"){
                    Flash(
                        color = color.value,
                        interval = interval.floatValue,
                        brightness = brightness.floatValue
                    )
                }
                composable("linear"){
                    LinearFlash(
                        colors = colors
                    )
                }
                composable("techno"){
                    TechnoFlash(
                        colors = colors
                    )
                }
                composable("rgb"){
                    RgbFlash()
                }
            }
        }
    }
}
