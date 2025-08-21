package com.example.rc_car_control_ble.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.rc_car_control_ble.domain.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}