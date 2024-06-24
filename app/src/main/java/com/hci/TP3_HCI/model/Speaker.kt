package com.hci.TP3_HCI.model

import com.hci.TP3_HCI.remote.model.RemoteDevice
import com.hci.TP3_HCI.remote.model.devices.speaker.RemoteSpeaker
import com.hci.TP3_HCI.remote.model.devices.speaker.RemoteSpeakerState

class Speaker(
    id: String?,
    name: String,
    val status: Status,
    val volume: Float,
    val genre: String,
    val song: SpeakerSong?,
) : Device(id, name, DeviceType.SPEAKER) {

    override fun asRemoteModel(): RemoteDevice<RemoteSpeakerState> {
        val state = RemoteSpeakerState()
        state.status = Status.asRemoteModel(status)
        state.volume = volume
        state.genre = genre
        state.song = song?.asRemoteModel()

        val model = RemoteSpeaker()
        model.id = id
        model.name = name
        model.setState(state)
        return model
    }

    companion object {
        const val SET_VOLUME_ACTION = "setVolume"
        const val PLAY_ACTION = "play"
        const val STOP_ACTION = "stop"
        const val PAUSE_ACTION = "pause"
        const val RESUME_ACTION = "resume"
        const val NEXT_SONG_ACTION = "nextSong"
        const val PREVIOUS_SONG_ACTION = "previousSong"
        const val SET_GENRE_ACTION = "setGenre"
        const val GET_PLAYLIST_ACTION = "getPlaylist"
    }
}