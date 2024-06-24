package com.hci.TP3_HCI.model

import com.hci.TP3_HCI.remote.model.RemoteDevice
import com.hci.TP3_HCI.remote.model.devices.sprinkler.RemoteSprinkler
import com.hci.TP3_HCI.remote.model.devices.sprinkler.RemoteSprinklerState

class Sprinkler(
    id: String?,
    name: String,
    val quantity: Float?,
    val unit: String?,
    val dispensedQuantity: Float?,
    val status: SprinklerStatus
) : Device(id, name, DeviceType.SPRINKLER) {

    override fun asRemoteModel(): RemoteDevice<RemoteSprinklerState> {
        val state = RemoteSprinklerState()
        state.status = SprinklerStatus.asRemoteModel(status)
        state.quantity = quantity
        state.unit = unit
        state.dispensedQuantity = dispensedQuantity

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
