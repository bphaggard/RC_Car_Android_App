package com.example.rc_car_control_ble.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rc_car_control_ble.R
import com.example.rc_car_control_ble.ui.theme.back_yellow
import com.example.rc_car_control_ble.ui.theme.merc_red
import groteskFamily

@Composable
fun BackgroundScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(back_yellow)
    ){
        Image(
            painter = painterResource(id = R.drawable.car_background),
            contentDescription = "Background",
            modifier = Modifier.matchParentSize()
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

            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = merc_red)
            ) {
                Text("Connect to HM-10")
            }
            Spacer(modifier = Modifier.height(16.dp))
            ControlButton(0f, "forward")
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ControlButton(270f, "left")
                ControlButton(90f, "right")
            }
            Spacer(modifier = Modifier.height(8.dp))
            ControlButton(180f, "backward")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _root_ide_package_.com.example.rc_car_control_ble.ui.theme.RC_CAR_CONTROL_BLETheme {
        BackgroundScreen()
    }
}