package com.hci.TP3_HCI.model

import com.hci.TP3_HCI.remote.model.devices.speaker.RemoteSpeakerSong

class SpeakerSong(
    val title: String,
    val artist: String,
    val album: String,
    val duration: String,
    val progress: String
) {
    fun asRemoteModel(): RemoteSpeakerSong {
        val remoteSong = RemoteSpeakerSong()
        remoteSong.title = title
        remoteSong.artist = artist
        remoteSong.album = album
        remoteSong.duration = duration
        remoteSong.progress = progress
        return remoteSong
    }
}
