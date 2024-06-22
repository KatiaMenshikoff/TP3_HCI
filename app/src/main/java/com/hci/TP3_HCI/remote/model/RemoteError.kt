package com.hci.TP3_HCI.remote.model

import com.google.gson.annotations.SerializedName

class RemoteError {

    @SerializedName("code")
    var code: Int = 0

    @SerializedName("description")
    lateinit var description: List<String>
}
