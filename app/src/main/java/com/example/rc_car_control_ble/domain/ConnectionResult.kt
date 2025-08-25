package com.example.rc_car_control_ble.domain

sealed interface ConnectionResult {
    object ConnectionEstablished: ConnectionResult
    data class Error(val message: String): ConnectionResult
}