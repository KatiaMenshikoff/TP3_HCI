package com.hci.TP3_HCI.ui.sprinkler

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hci.TP3_HCI.DataSourceException
import com.hci.TP3_HCI.model.Error
import com.hci.TP3_HCI.model.Sprinkler
import com.hci.TP3_HCI.model.SprinklerStatus
import com.hci.TP3_HCI.repository.DeviceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SprinklerViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SprinklerUiState())
    val uiState = _uiState.asStateFlow()

    fun setCurrentDevice(deviceId: String){
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, response -> state.copy(currentDevice = response as Sprinkler?) }
        )
    }

    fun startPeriodicUpdates(deviceId: String) {
        viewModelScope.launch {
            while (true) {
                updateDevice(deviceId)
                delay(5000) // Wait for 5 seconds before the next update
            }
        }
    }

    private suspend fun updateDevice(deviceId: String) {
        val device = repository.getDevice(deviceId)
        _uiState.update { it.copy(currentDevice = device as Sprinkler?) }
    }

    fun dispense(quantity: Int, unit: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Sprinkler.DISPENSE_ACTION, arrayOf(quantity, unit)) },
        { state, _ -> state }
    )

    fun open() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Sprinkler.OPEN_ACTION) },
        { state, _ ->
            val currentDevice = state.currentDevice
            if (currentDevice != null) {
//                currentDevice.status = SprinklerStatus.ON
            }
            state
        }
    )

    fun close() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Sprinkler.CLOSE_ACTION) },
        { state, _ ->
            val currentDevice = state.currentDevice
            if (currentDevice != null) {
//                currentDevice.status = SprinklerStatus.OFF
            }
            state
        }
    )

    private fun <T> runOnViewModelScope(
        block: suspend () -> T,
        updateState: (SprinklerUiState, T) -> SprinklerUiState
    ): Job = viewModelScope.launch {
        _uiState.update { it.copy(loading = true, error = null) }
        runCatching {
            block()
        }.onSuccess { response ->
            _uiState.update { updateState(it, response).copy(loading = false) }
        }.onFailure { e ->
            _uiState.update { it.copy(loading = false, error = handleError(e)) }
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }
}
