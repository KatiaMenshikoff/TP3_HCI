package com.hci.TP3_HCI.remote.model.devices.speaker

import com.hci.TP3_HCI.model.Speaker
import com.hci.TP3_HCI.remote.model.RemoteDevice
import com.hci.TP3_HCI.remote.model.RemoteStatus

class RemoteSpeaker : RemoteDevice<RemoteSpeakerState>() {

    override fun asModel(): Speaker {
        return Speaker(
            id = id,
            name = name,
            status = RemoteStatus.asModel(state.status),
            volume = state.volume,
            genre = state.genre,
            song = state.song?.asModel()
        )
    }
}
