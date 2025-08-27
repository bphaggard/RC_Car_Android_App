package com.example.rc_car_control_ble

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rc_car_control_ble.presentation.ui.BackgroundScreen
import com.example.rc_car_control_ble.presentation.ui.DeviceListScreen
import com.example.rc_car_control_ble.presentation.viewmodel.BleViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<BleViewModel>()

    NavHost(navController, startDestination = Screen.StartScreen.route) {
        composable(
            route = Screen.StartScreen.route
        ) {
            BackgroundScreen(navController)
        }
        composable(
            route = Screen.DeviceScreen.route
        ) {
            DeviceListScreen(
                viewModel = viewModel,
                onDeviceSelected = { navController.navigate(Screen.StartScreen.route) },
                navController = navController
            )
        }
    }
}

sealed class Screen(val route: String) {
    object StartScreen : Screen("start_screen")
    object DeviceScreen : Screen("device_screen")
}