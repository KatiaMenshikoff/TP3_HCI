package com.hci.TP3_HCI.remote.model

import com.hci.TP3_HCI.model.Status

object RemoteStatus {
    const val ON = "on"
    const val OFF = "off"

    fun asModel(status: String): Status {
        return when (status) {
            ON -> Status.ON
            else -> Status.OFF
        }
    }
}