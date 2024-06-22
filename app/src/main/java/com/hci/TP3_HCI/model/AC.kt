package com.hci.TP3_HCI.model

import com.hci.TP3_HCI.remote.model.RemoteDevice
import com.hci.TP3_HCI.remote.model.devices.RemoteAC
import com.hci.TP3_HCI.remote.model.devices.RemoteACState

class AC(
    id: String?,
    name: String,
    val status: Status,
    val temperature: Int,
    val mode: String,
    val verticalSwing: String,
    val horizontalSwing: String,
    val fanSpeed: String
) : Device(id, name, DeviceType.AC){
    override fun asRemoteModel(): RemoteDevice<RemoteACState> {
        val state = RemoteACState()
        state.status = Status.asRemoteModel(status)
        state.temperature = temperature
        state.mode = mode
        state.verticalSwing = verticalSwing
        state.horizontalSwing = horizontalSwing

        val model = RemoteAC()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }
    companion object{
        const val TURN_ON_ACTION = "turnOn"
        const val TURN_OFF_ACTION = "turnOff"
        const val SET_TEMPERATURE_ACTION = "setTemperature"
        const val SET_MODE_ACTION = "setMode"
        const val SET_VERTICAL_SWING_ACTION = "setVerticalSwing"
        const val SET_HORIZONTAL_SWING_ACTION = "setHorizontalSwing"
        const val SET_FAN_SPEED_ACTION = "setFanSpeed"
    }
}