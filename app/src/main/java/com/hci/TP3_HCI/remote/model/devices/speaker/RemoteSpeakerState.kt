package com.hci.TP3_HCI.remote.model.devices.speaker

import com.google.gson.annotations.SerializedName

class RemoteSpeakerState {
    @SerializedName("status")
    lateinit var status: String

    @SerializedName("volume")
    var volume: Float = 0f

    @SerializedName("genre")
    lateinit var genre: String

    @SerializedName("song")
    var song: RemoteSpeakerSong? = null
}
