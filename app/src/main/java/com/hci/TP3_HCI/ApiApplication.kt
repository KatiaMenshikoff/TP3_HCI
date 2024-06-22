package com.hci.TP3_HCI

import android.app.Application
import com.hci.TP3_HCI.remote.DeviceRemoteDataSource
import com.hci.TP3_HCI.remote.RoomRemoteDataSource
import com.hci.TP3_HCI.remote.api.RetrofitClient
import com.hci.TP3_HCI.repository.DeviceRepository
import com.hci.TP3_HCI.repository.RoomRepository

class ApiApplication  : Application() {

    private val roomRemoteDataSource: RoomRemoteDataSource
        get() = RoomRemoteDataSource(RetrofitClient.roomService)

    private val deviceRemoteDataSource: DeviceRemoteDataSource
        get() = DeviceRemoteDataSource(RetrofitClient.deviceService)

    val roomRepository: RoomRepository
        get() = RoomRepository(roomRemoteDataSource)

    val deviceRepository: DeviceRepository
        get() = DeviceRepository(deviceRemoteDataSource)
}