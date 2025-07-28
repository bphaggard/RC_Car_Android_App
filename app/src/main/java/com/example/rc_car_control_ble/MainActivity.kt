package com.example.rc_car_control_ble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.rc_car_control_ble.presentation.ui.BackgroundScreen
import com.example.rc_car_control_ble.ui.theme.RC_CAR_CONTROL_BLETheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RC_CAR_CONTROL_BLETheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    BackgroundScreen()
                }
            }
        }
    }
}