package com.hci.TP3_HCI.ui.devices

import com.hci.TP3_HCI.model.Error
import com.hci.TP3_HCI.model.Lamp

data class LampUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: Lamp? = null
)

val LampUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading