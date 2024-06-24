package com.hci.TP3_HCI.remote.model.devices.ac

import com.google.gson.annotations.SerializedName

class RemoteACState {
    @SerializedName("status")
    lateinit var status: String

    @SerializedName("temperature")
    var temperature: Int = 0

    @SerializedName("mode")
    lateinit var mode: String

    @SerializedName("verticalSwing")
    lateinit var verticalSwing: String

    @SerializedName("horizontalSwing")
    lateinit var horizontalSwing: String

    @SerializedName("fanSpeed")
    lateinit var fanSpeed: String
}