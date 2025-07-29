package com.example.rc_car_control_ble.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class BleDevice(val name: String?, val address: String)

class BleViewModel(app: Application) : AndroidViewModel(app) {

    private val _devices = MutableStateFlow<List<BleDevice>>(emptyList())
    val devices = _devices.asStateFlow()

    private val bluetoothManager = app.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    private val scanner: BluetoothLeScanner? = bluetoothAdapter?.bluetoothLeScanner

    private val foundDevices = mutableMapOf<String, BleDevice>()

    private val scanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            val newDevice = BleDevice(device.name ?: "Unknown", device.address)
            if (!foundDevices.containsKey(device.address)) {
                foundDevices[device.address] = newDevice
                _devices.value = foundDevices.values.toList()
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e("BLE", "Scan failed with error $errorCode")
        }
    }

    @SuppressLint("MissingPermission")
    fun startScan(durationMillis: Long = 10000) {
        scanner?.startScan(scanCallback)
        viewModelScope.launch {
            delay(durationMillis)
            stopScan()
        }
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        scanner?.stopScan(scanCallback)
    }
}
