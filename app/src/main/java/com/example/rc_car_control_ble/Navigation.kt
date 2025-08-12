package com.example.rc_car_control_ble

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rc_car_control_ble.presentation.ui.BackgroundScreen
import com.example.rc_car_control_ble.presentation.ui.DeviceListScreen
import com.example.rc_car_control_ble.presentation.viewmodel.BleViewModel

@Composable
fun Navigation(viewModel: BleViewModel = viewModel()) {
    val navController = rememberNavController()
    val devices by viewModel.devices.collectAsState()

    NavHost(navController, startDestination = Screen.StartScreen.route) {
        composable(
            route = Screen.StartScreen.route
        ) {
            BackgroundScreen(navController)
        }
        composable(
            route = Screen.DeviceScreen.route
        ) {
            DeviceListScreen(viewModel)
        }
    }
}

sealed class Screen(val route: String) {
    object StartScreen : Screen("start_screen")
    object DeviceScreen : Screen("device_screen")
}