package com.hci.TP3_HCI.ui.speaker

import com.hci.TP3_HCI.model.Device
import com.hci.TP3_HCI.model.Error
import com.hci.TP3_HCI.model.Speaker

data class SpeakerUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Speaker? = null,
    val playlist: Array<*>? = null
)

val SpeakerUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading