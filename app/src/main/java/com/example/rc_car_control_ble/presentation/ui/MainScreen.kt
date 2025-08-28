package com.example.rc_car_control_ble.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rc_car_control_ble.R
import com.example.rc_car_control_ble.Screen
import com.example.rc_car_control_ble.presentation.viewmodel.BleViewModel
import com.example.rc_car_control_ble.ui.theme.back_yellow
import com.example.rc_car_control_ble.ui.theme.merc_red
import groteskFamily

@Composable
fun BackgroundScreen(
    viewModel: BleViewModel,
    navController: NavController){

    val isConnected by viewModel.isConnected.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(back_yellow)
    ){
        Image(
            painter = painterResource(id = R.drawable.car_background),
            contentDescription = "Background",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Text(
                text = "Mercedes Benz 190E",
                fontFamily = groteskFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.Black
            )
            Text(
                text = "BT Evo control",
                fontFamily = groteskFamily,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Button(
                    onClick = { navController.navigate(Screen.DeviceScreen.route) },
                    colors = ButtonDefaults.buttonColors(containerColor = merc_red)
                ) {
                    Text("Connect to HM-10")
                }
                IconButton(
                    onClick = { viewModel.disconnect() },
                    modifier = Modifier
                        .size(56.dp),
                    enabled = isConnected
                )
                {
                    Icon(
                        painter = painterResource(R.drawable.outline_bluetooth_disabled_24),
                        contentDescription = "lights on/off",
                        modifier = Modifier
                            .size(34.dp),
                        tint = if (isConnected) Color.Black else Color.Gray.copy(alpha = 0.5f)
                    )
                }
                IconButton(
                    onClick = { viewModel.toggleLights() },
                    modifier = Modifier
                        .size(56.dp),
                    enabled = isConnected
                )
                {
                    Icon(
                        painter = painterResource(
                            if (viewModel.lightsOn) {
                                R.drawable.outline_lightbulb_24
                            } else {
                                R.drawable.outline_light_off_24
                            }
                        ),
                        contentDescription = "lights on/off",
                        modifier = Modifier
                            .size(34.dp),
                        tint = if (isConnected) Color.Black else Color.Gray.copy(alpha = 0.5f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(0.4f),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ControlButton(
                        0f,
                        "forward",
                        { viewModel.sendToHm10("dc:motor.forward") },
                        { viewModel.sendToHm10("dc:motor.stop") })
                    ControlButton(
                        180f,
                        "backward",
                        { viewModel.sendToHm10("dc:motor.backward") },
                        { viewModel.sendToHm10("dc:motor.stop") })
                }
                Row(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ControlButton(
                        270f,
                        "left",
                        { viewModel.sendToHm10("servo.left") },
                        { viewModel.sendToHm10("servo.center") })
                    Spacer(modifier = Modifier.height(10.dp))
                    ControlButton(
                        90f,
                        "right",
                        { viewModel.sendToHm10("servo.right") },
                        { viewModel.sendToHm10("servo.center") })
                }
            }
        }
    }
}