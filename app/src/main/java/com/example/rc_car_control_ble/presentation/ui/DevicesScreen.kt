package com.example.rc_car_control_ble.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.rc_car_control_ble.presentation.viewmodel.BleDevice
import com.example.rc_car_control_ble.presentation.viewmodel.BleViewModel

@Composable
fun DeviceListScreen(
    viewModel: BleViewModel,
) {
    val devices by viewModel.devices.collectAsState()

    LazyColumn {
        items(devices) { device ->
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        }
    }
}
