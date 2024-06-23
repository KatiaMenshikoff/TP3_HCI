package com.hci.TP3_HCI.repository

import com.hci.TP3_HCI.model.Device
import com.hci.TP3_HCI.model.Lamp
import com.hci.TP3_HCI.remote.DeviceRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class DeviceRepository(
    private val remoteDataSource: DeviceRemoteDataSource
) {
    val devices: Flow<List<Device>> =
        remoteDataSource.devices
            .map { it.map { jt -> jt.asModel() } }

    //    val currentDevice = devices.map { it.firstOrNull { jt -> jt is Lamp } }

    val currentDeviceId = MutableStateFlow<String?>(null)

    val currentDevice: Flow<Device?> = combine(devices, currentDeviceId) { devices, id ->
        id?.let { deviceId ->
            devices.firstOrNull { it.id == deviceId }
        }
    }

    fun setCurrentDeviceId(deviceId: String?) {
        currentDeviceId.value = deviceId
    }

    suspend fun getDevice(deviceId: String): Device {
        return remoteDataSource.getDevice(deviceId).asModel()
    }

    suspend fun addDevice(device: Device): Device {
        return remoteDataSource.addDevice(device.asRemoteModel()).asModel()
    }

    suspend fun modifyDevice(device: Device): Boolean {
        return remoteDataSource.modifyDevice(device.asRemoteModel())
    }

    suspend fun deleteDevice(deviceId: String): Boolean {
        return remoteDataSource.deleteDevice(deviceId)
    }

    suspend fun executeDeviceAction(
        deviceId: String,
        action: String,
        parameters: Array<*> = emptyArray<Any>()
    ): Array<*> {
        return remoteDataSource.executeDeviceAction(deviceId, action, parameters)
    }
}