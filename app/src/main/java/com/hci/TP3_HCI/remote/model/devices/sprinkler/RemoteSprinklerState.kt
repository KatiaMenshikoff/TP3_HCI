package com.hci.TP3_HCI.remote.model.devices.sprinkler

import com.google.gson.annotations.SerializedName

class RemoteSprinklerState {
    @SerializedName("status")
    lateinit var status: String

    @SerializedName("quantity")
    var quantity: Float? = null

    @SerializedName("unit")
    var unit: String? = null

    @SerializedName("dispensedQuantity")
    var dispensedQuantity: Float? = null
}
