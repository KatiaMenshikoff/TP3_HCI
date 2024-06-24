package com.hci.TP3_HCI.remote.model.devices.ac

import com.hci.TP3_HCI.model.AC
import com.hci.TP3_HCI.remote.model.RemoteDevice
import com.hci.TP3_HCI.remote.model.RemoteStatus

class RemoteAC : RemoteDevice<RemoteACState>() {

    override fun asModel(): AC {
        return AC(
            id = id,
            name = name,
            status = RemoteStatus.asModel(state.status),
            temperature = state.temperature,
            mode = state.mode,
            verticalSwing = state.verticalSwing,
            horizontalSwing = state.horizontalSwing,
            fanSpeed = state.fanSpeed
        )
    }
}