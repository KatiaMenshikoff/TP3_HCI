package com.hci.TP3_HCI.remote.model.devices

import com.hci.TP3_HCI.model.Sprinkler
import com.hci.TP3_HCI.remote.model.RemoteDevice
import com.hci.TP3_HCI.remote.model.RemoteStatus

class RemoteSprinkler : RemoteDevice<RemoteSprinklerState>() {

    override fun asModel(): Sprinkler {
        return Sprinkler(
            id = id,
            name = name,
            status = RemoteStatus.asModel(state.status)
        )
    }
}
