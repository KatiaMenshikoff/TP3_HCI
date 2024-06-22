package com.hci.TP3_HCI.model

import com.hci.TP3_HCI.remote.model.RemoteDevice
import com.hci.TP3_HCI.remote.model.devices.RemoteSprinkler
import com.hci.TP3_HCI.remote.model.devices.RemoteSprinklerState

class Sprinkler(
    id: String?,
    name: String,
    val status: Status
) : Device(id, name, DeviceType.SPRINKLER) {

    override fun asRemoteModel(): RemoteDevice<RemoteSprinklerState> {
        val state = RemoteSprinklerState()
        state.status = Status.asRemoteModel(status)

        val model = RemoteSprinkler()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }

    companion object {
        const val OPEN_ACTION = "open"
        const val CLOSE_ACTION = "close"
        const val DISPENSE_ACTION = "dispense"
    }
}
