package com.hci.TP3_HCI.remote.model.devices

import com.google.gson.annotations.SerializedName

class RemoteSprinklerState {
    @SerializedName("status")
    lateinit var status: String
}
