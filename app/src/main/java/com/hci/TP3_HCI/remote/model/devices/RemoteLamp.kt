package com.hci.TP3_HCI.remote.model.devices

import com.hci.TP3_HCI.model.Lamp
import com.hci.TP3_HCI.remote.model.RemoteDevice
import com.hci.TP3_HCI.remote.model.RemoteStatus

class RemoteLamp : RemoteDevice<RemoteLampState>() {

    override fun asModel(): Lamp {
        return Lamp(
            id = id,
            name = name,
            status = RemoteStatus.asModel(state.status),
            color = state.color,
            brightness = state.brightness
        )
    }
}