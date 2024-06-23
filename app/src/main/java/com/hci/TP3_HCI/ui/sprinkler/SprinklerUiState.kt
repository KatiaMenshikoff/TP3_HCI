package com.hci.TP3_HCI.ui.sprinkler

import com.hci.TP3_HCI.model.Error
import com.hci.TP3_HCI.model.Sprinkler

data class SprinklerUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Sprinkler? = null
)

val SprinklerUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading