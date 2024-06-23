package com.hci.TP3_HCI.ui.ac

import com.hci.TP3_HCI.model.AC
import com.hci.TP3_HCI.model.Error

data class ACUiState(
    val loading: Boolean = false,
    val error: Error? = null,
    val currentDevice: AC? = null
)

val ACUiState.canExecuteAction: Boolean get() = currentDevice != null && !loading