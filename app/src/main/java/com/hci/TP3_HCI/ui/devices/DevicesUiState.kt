package com.hci.TP3_HCI.ui.devices

import com.hci.TP3_HCI.model.Error
import com.hci.TP3_HCI.model.Device

data class DevicesUiState(
    val isFetching: Boolean = false,
    val error: Error? = null,
    val devices: List<Device> = emptyList()
)