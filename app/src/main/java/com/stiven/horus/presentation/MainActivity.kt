/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.stiven.horus.presentation

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
        val wakeLock = (this.getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.FULL_WAKE_LOCK, "Horus::MyWakeLock")
        }
        setContent {
            WearApp(wakeLock)
        }
    }
}

@Composable
fun WearApp(wakeLock: PowerManager.WakeLock) {
    val context = LocalContext.current
    val systemBrightness = remember {
        mutableIntStateOf(Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS))
    }
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
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TimeText()
            SwipeDismissableNavHost(
                navController = navController,
                startDestination = "root"
            ) {
                composable("root"){
                    releaseWakeLock(wakeLock)
                    setBrightness(context, mapIntToFloat(systemBrightness.intValue))
                    MainPage(
                        navController = navController,
                    )
                }
                composable("mono-color"){
                    releaseWakeLock(wakeLock)
                    setBrightness(context, mapIntToFloat(systemBrightness.intValue))
                    MonoColor(
                        navController = navController,
                        color = color,
                        brightness = brightness,
                        interval = interval
                    )
                }
                composable("modes"){
                    releaseWakeLock(wakeLock)
                    setBrightness(context, mapIntToFloat(systemBrightness.intValue))
                    Modes(
                        navController = navController,
                        brightness = brightness
                    )
                }
                composable("flashlight"){
                    acquireWakeLock(wakeLock)
                    setBrightness(context, mapIntToFloat(systemBrightness.intValue))
                    FlashLight(context)
                }
                composable("flash"){
                    acquireWakeLock(wakeLock)
                    Flash(
                        color = color.value,
                        interval = interval.floatValue,
                        brightness = brightness.floatValue,
                    )
                }
                composable("linear"){
                    acquireWakeLock(wakeLock)
                    LinearFlash(
                        colors = colors,
                    )
                }
                composable("techno"){
                    acquireWakeLock(wakeLock)
                    TechnoFlash(
                        colors = colors,
                    )
                }
                composable("rgb"){
                    acquireWakeLock(wakeLock)
                    RgbFlash()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FlashLight(context: Context) {
    setBrightness(context, 1f)
    val brightness = remember {
        mutableFloatStateOf(1f)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .combinedClickable(
                onClick = {
                    Toast
                        .makeText(context, "Long press to change intensity.", Toast.LENGTH_SHORT)
                        .show()
                },
                onLongClick = {
                    Log.d("BRG", brightness.floatValue.toString())
                    brightness.floatValue -= 0.3f
                    if (brightness.floatValue <= 0f) {
                        setBrightness(context, 1f)
                        brightness.floatValue = 1f
                    } else {
                        setBrightness(context, brightness.floatValue)
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        // Display anything you want in the center
    }
}

fun mapIntToFloat(value: Int): Float {
    return value / 255f
}

fun releaseWakeLock(wakeLock: WakeLock){
    if (wakeLock.isHeld){
        wakeLock.release()
    }
}

fun acquireWakeLock(wakeLock: WakeLock){
    wakeLock.acquire(10*60*1000L /*10 minutes*/)

}