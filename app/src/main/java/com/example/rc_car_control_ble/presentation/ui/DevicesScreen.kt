package com.example.rc_car_control_ble.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.rc_car_control_ble.presentation.viewmodel.BleViewModel
import com.example.rc_car_control_ble.ui.theme.back_yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceListScreen(
    viewModel: BleViewModel,
    onDeviceSelected: () -> Unit,
    navController : NavController,
) {
    val context = LocalContext.current

    // Permissions launcher
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            viewModel.startScanSafe(context)
        } else {
            Toast.makeText(context, "Permissions denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        val requiredPerms = mutableListOf<String>().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                add(Manifest.permission.BLUETOOTH_SCAN)
                add(Manifest.permission.BLUETOOTH_CONNECT)
            }
            add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        val granted = requiredPerms.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if (!granted) {
            // Request permissions only if not yet granted
            launcher.launch(requiredPerms.toTypedArray())
        } else {
            viewModel.startScanSafe(context)
        }
    }

    val devices by viewModel.devices.collectAsState()

    Scaffold(
        containerColor = back_yellow,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = ""
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "backIcon",
                            tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
            )
        }, content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(back_yellow)
            ) {
                Text(
                    "Available Devices",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = back_yellow)
                ) {
                    items(devices) { device ->
                        Text(
                            text = device.name ?: "(No name)",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Only connect if permission is granted
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                                        ContextCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.BLUETOOTH_CONNECT
                                        ) != PackageManager.PERMISSION_GRANTED
                                    ) {
                                        Toast.makeText(context, "Connect permission required", Toast.LENGTH_SHORT).show()
                                    }
                                    viewModel.connect(device, context)
                                    onDeviceSelected()
                                }
                                .padding(16.dp),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    )
}