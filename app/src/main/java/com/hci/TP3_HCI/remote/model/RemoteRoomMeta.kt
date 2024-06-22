package com.hci.TP3_HCI.remote.model

import com.google.gson.annotations.SerializedName

class RemoteRoomMeta {
    @SerializedName("size")
    lateinit var size: String

    @SerializedName("color")
    lateinit var color: String
}