package com.hci.TP3_HCI.model

import com.hci.TP3_HCI.remote.model.RemoteSpeakerStatus

enum class SpeakerStatus {
    PLAYING, PAUSED, STOPPED;

    companion object {
        fun asRemoteModel(value: SpeakerStatus): String {
            return when (value) {
                PLAYING -> RemoteSpeakerStatus.PLAYING
                PAUSED -> RemoteSpeakerStatus.PAUSED
                STOPPED -> RemoteSpeakerStatus.STOPPED
            }
        }
    }
}