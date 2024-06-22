package com.hci.TP3_HCI

import android.app.Application
import com.hci.TP3_HCI.remote.DeviceRemoteDataSource
import com.hci.TP3_HCI.remote.api.RetrofitClient
import com.hci.TP3_HCI.repository.DeviceRepository

class ApiApplication  : Application() {
    private val deviceRemoteDataSource: DeviceRemoteDataSource
        get() = DeviceRemoteDataSource(RetrofitClient.deviceService)

    val deviceRepository: DeviceRepository
        get() = DeviceRepository(deviceRemoteDataSource)
}