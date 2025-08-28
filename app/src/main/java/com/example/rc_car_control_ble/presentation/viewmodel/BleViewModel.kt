package com.example.rc_car_control_ble.presentation.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BleViewModel @Inject constructor(
    private val app: Application
): AndroidViewModel(app){
    companion object {
        val UART_SERVICE_UUID: UUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")
        val UART_RX_UUID: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb") // Write
        val UART_TX_UUID: UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb") // Notify
        const val TAG = "BleViewModel"
    }

    var lightsOn by mutableStateOf(false)
        private set

    fun toggleLights() {
        lightsOn = !lightsOn
        if (lightsOn) {
            sendToHm10("lights.on")
        } else {
            sendToHm10("lights.off")
        }
    }

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _devices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val devices: StateFlow<List<BluetoothDevice>> = _devices

    private var bluetoothGatt: BluetoothGatt? = null
    private var txCharacteristic: BluetoothGattCharacteristic? = null
    private var rxCharacteristic: BluetoothGattCharacteristic? = null

    @SuppressLint("MissingPermission")
    fun startScanSafe(context: Context) {
        if (!hasBlePermissions(context)) {
            Log.d(TAG, "Scan blocked: Permissions not granted")
            return
        }

        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val adapter = bluetoothManager.adapter
        val scanner = adapter.bluetoothLeScanner ?: return

        val callback = object : android.bluetooth.le.ScanCallback() {
            override fun onScanResult(callbackType: Int, result: android.bluetooth.le.ScanResult) {
                val device = result.device
                if (!_devices.value.contains(device)) {
                    _devices.value = _devices.value + device
                }
            }
        }
        scanner.startScan(callback)
    }

    @SuppressLint("MissingPermission")
    fun connect(device: BluetoothDevice, context: Context) {
        if (!hasBlePermissions(context)) return

        bluetoothGatt = device.connectGatt(context, false, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.d(TAG, "Connected to ${device.address}")
                    gatt.discoverServices()
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.d(TAG, "Disconnected from ${device.address}")
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    Log.d(TAG, "Services discovered")
                }
            }

            override fun onCharacteristicChanged(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic
            ) {
                val value = characteristic.value
                Log.d(TAG, "Received: ${value.toString(Charsets.UTF_8)}")
            }
        })
        _isConnected.value = true
    }

    @SuppressLint("MissingPermission")
    fun disconnect() {
        bluetoothGatt?.close()
        bluetoothGatt = null
        _isConnected.value = false
    }

    @SuppressLint("MissingPermission")
    fun sendToHm10(message: String) {
        val gatt = bluetoothGatt ?: return
        val service = gatt.getService(UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")) ?: return
        val characteristic = service.getCharacteristic(UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")) ?: return

        characteristic.value = message.toByteArray()
        gatt.writeCharacteristic(characteristic)
    }


    private val gattCallback = object : BluetoothGattCallback() {

        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d(TAG, "Connected to ${gatt.device.address}")
                gatt.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.d(TAG, "Disconnected from ${gatt.device.address}")
            }
        }

        @SuppressLint("MissingPermission")
        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            val uartService = gatt.getService(UART_SERVICE_UUID)
            if (uartService != null) {
                rxCharacteristic = uartService.getCharacteristic(UART_RX_UUID)
                txCharacteristic = uartService.getCharacteristic(UART_TX_UUID)
                gatt.setCharacteristicNotification(txCharacteristic, true)
                Log.d(TAG, "UART Service found and notifications enabled")
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
            val value = characteristic.value
            Log.d(TAG, "Received: ${String(value)}")
            // You can update a MutableStateFlow here for Compose UI
        }
    }

    private fun hasBlePermissions(context: Context): Boolean {
        val perms = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            perms.add(Manifest.permission.BLUETOOTH_SCAN)
            perms.add(Manifest.permission.BLUETOOTH_CONNECT)
        }
        perms.add(Manifest.permission.ACCESS_FINE_LOCATION)

        return perms.all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
    }
}