package com.example.rc_car_control_ble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rc_car_control_ble.ui.theme.Purple40
import com.example.rc_car_control_ble.ui.theme.RC_CAR_CONTROL_BLETheme
import com.example.rc_car_control_ble.ui.theme.back_yellow
import java.nio.file.WatchEvent

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
                fontSize = 30.sp,
                color = Color.Black
            )
            Text(
                text = "BT Evo control",
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {  }) {
                Text("Connect to HM-10")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedIconButton(onClick = {},
                border= BorderStroke(5.dp, Color.Black),) {
                Icon(
                    painter = painterResource(R.drawable.rounded_arrow_upward_24),
                    contentDescription = "forward"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedIconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.rounded_arrow_upward_24),
                    contentDescription = "forward"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RC_CAR_CONTROL_BLETheme {
        BackgroundScreen()
    }
}