package com.hci.TP3_HCI.remote.model.devices.sprinkler

import com.hci.TP3_HCI.model.Sprinkler
import com.hci.TP3_HCI.remote.model.RemoteDevice
import com.hci.TP3_HCI.remote.model.RemoteSprinklerStatus

class RemoteSprinkler : RemoteDevice<RemoteSprinklerState>() {

    override fun asModel(): Sprinkler {
        return Sprinkler(
            id = id,
            name = name,
            status = RemoteSprinklerStatus.asModel(state.status),
            quantity = state.quantity,
            unit = state.unit,
            dispensedQuantity = state.dispensedQuantity
        )
    }
}
