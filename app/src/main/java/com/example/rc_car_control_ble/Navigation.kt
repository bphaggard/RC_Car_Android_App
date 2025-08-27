package com.example.rc_car_control_ble

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rc_car_control_ble.presentation.ui.BackgroundScreen
import com.example.rc_car_control_ble.presentation.ui.DeviceListScreen
import com.example.rc_car_control_ble.presentation.viewmodel.BluetoothViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<BluetoothViewModel>()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = state.errorMessage) {
        state.errorMessage?.let { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(key1 = state.isConnected) {
        if (state.isConnected) {
            Toast.makeText(
                context,
                "You're connected!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    NavHost(navController, startDestination = Screen.StartScreen.route) {
        composable(
            route = Screen.StartScreen.route
        ) {
            BackgroundScreen(navController)
        }
        composable(
            route = Screen.DeviceScreen.route
        ) {
            when {
                state.isConnecting -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Text(text = "Connecting...")
                    }
                }
                else ->
                    DeviceListScreen(
                        state = state,
                        onStartScan = viewModel::startScan,
                        onStopScan = viewModel::stopScan,
                        onDeviceClick = viewModel::connectToDevice,
                        onStartServer = viewModel::waitForIncomingConnections,
                        navController = navController
                    )
            }
        }
    }
}

sealed class Screen(val route: String) {
    object StartScreen : Screen("start_screen")
    object DeviceScreen : Screen("device_screen")
}