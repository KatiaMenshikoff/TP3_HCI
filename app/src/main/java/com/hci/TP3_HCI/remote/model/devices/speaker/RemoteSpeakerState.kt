package com.hci.TP3_HCI.remote.model.devices.speaker

import com.google.gson.annotations.SerializedName

class RemoteSpeakerState {
    @SerializedName("status")
    lateinit var status: String

    @SerializedName("volume")
    var volume: Int = 0

    @SerializedName("genre")
    lateinit var genre: String

    @SerializedName("song")
    lateinit var song: RemoteSpeakerSong
}
