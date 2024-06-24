package com.hci.TP3_HCI.remote.model

import com.hci.TP3_HCI.model.SpeakerStatus

object RemoteSpeakerStatus {
    const val PLAYING = "playing"
    const val PAUSED = "paused"
    const val STOPPED = "stopped"

    fun asModel(status: String): SpeakerStatus {
        return when (status) {
            PLAYING -> SpeakerStatus.PLAYING
            PAUSED -> SpeakerStatus.PAUSED
            else -> SpeakerStatus.STOPPED
        }
    }
}