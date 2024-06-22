package com.hci.TP3_HCI.model

import com.hci.TP3_HCI.remote.model.RemoteStatus

enum class Status {
    ON, OFF;

    companion object {
        fun asRemoteModel(value: Status): String {
            return when (value) {
                ON -> RemoteStatus.ON
                else -> RemoteStatus.OFF
            }
        }
    }
}