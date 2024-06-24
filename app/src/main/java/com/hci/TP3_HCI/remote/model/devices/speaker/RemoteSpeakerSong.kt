package com.hci.TP3_HCI.remote.model.devices.speaker

import com.google.gson.annotations.SerializedName
import com.hci.TP3_HCI.model.SpeakerSong

class RemoteSpeakerSong {
    @SerializedName("title")
    lateinit var title: String

    @SerializedName("artist")
    lateinit var artist: String

    @SerializedName("album")
    lateinit var album: String

    @SerializedName("duration")
    lateinit var duration: String

    @SerializedName("progress")
    lateinit var progress: String

    fun asModel(): SpeakerSong {
        return SpeakerSong(
            title = title,
            artist = artist,
            album = album,
            duration = duration,
            progress = progress
        )
    }
}
