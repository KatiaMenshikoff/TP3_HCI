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

    // StateFlow mutable que se utiliza para mantener el estado del ID del dispositivo actual.
    // Este flujo emite un nuevo valor cada vez que se actualiza su valor
    val currentDeviceId = MutableStateFlow<String?>(null)

    // combine toma dos flujos (devices y currentDeviceId) y emite un nuevo valor cada vez que cualquiera de
    // los dos flujos emite un nuevo valor. La combinación se realiza utilizando la función lambda proporcionada.
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