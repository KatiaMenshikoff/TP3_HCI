package com.hci.TP3_HCI.model

import com.hci.TP3_HCI.remote.model.RemoteDevice
import com.hci.TP3_HCI.remote.model.devices.lamp.RemoteLamp
import com.hci.TP3_HCI.remote.model.devices.lamp.RemoteLampState

class Lamp(
    id: String?,
    name: String,
    val status: Status,
    val color: String,
    val brightness: Int
) : Device(id, name, DeviceType.LAMP) {

    override fun asRemoteModel(): RemoteDevice<RemoteLampState> {
        val state = RemoteLampState()
        state.status = Status.asRemoteModel(status)
        state.color = color
        state.brightness = brightness

        val model = RemoteLamp()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }

    companion object {
        const val TURN_ON_ACTION = "turnOn"
        const val TURN_OFF_ACTION = "turnOff"
        const val SET_COLOR_ACTION = "setColor"
        const val SET_BRIGHTNESS_ACTION = "setBrightness"
    }
}