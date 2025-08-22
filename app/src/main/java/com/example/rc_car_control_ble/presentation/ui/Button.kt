package com.example.rc_car_control_ble.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rc_car_control_ble.R
import groteskFamily

@Composable
fun ControlButton(rotate: Float, direction: String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = direction,
            fontFamily = groteskFamily,
            fontSize = 14.sp,
            color = Color.Black
        )
        OutlinedIconButton(
            onClick = {},
            modifier = Modifier
                .size(56.dp),
            border = BorderStroke(5.dp, Color.Black)
        )
        {
            Icon(
                painter = painterResource(R.drawable.rounded_arrow_upward_24),
                contentDescription = null,
                modifier = Modifier
                    .size(34.dp)
                    .rotate(rotate),
                tint = Color.Black
            )
        }
    }
}