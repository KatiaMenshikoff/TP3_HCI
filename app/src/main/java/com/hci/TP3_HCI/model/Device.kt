package com.hci.TP3_HCI.model

import com.hci.TP3_HCI.remote.model.RemoteDevice

abstract class Device(
    val id: String?,
    val name: String,
    val type: DeviceType
) {
    abstract fun asRemoteModel(): RemoteDevice<*>
}