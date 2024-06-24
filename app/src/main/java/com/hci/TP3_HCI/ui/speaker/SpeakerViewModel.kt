package com.hci.TP3_HCI.ui.speaker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hci.TP3_HCI.DataSourceException
import com.hci.TP3_HCI.model.Error
import com.hci.TP3_HCI.model.Lamp
import com.hci.TP3_HCI.model.Speaker
import com.hci.TP3_HCI.repository.DeviceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class SpeakerViewModel(
    private val repository: DeviceRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SpeakerUiState())
    val uiState = _uiState.asStateFlow()

    fun setCurrentDevice(deviceId: String){
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, response -> state.copy(currentDevice = response as Speaker?) }
        )
    }
    
    fun startPeriodicUpdates(deviceId: String) {
        viewModelScope.launch {
            while (true) {
                updateDevice(deviceId)
                delay(5000) // Espera 5 segundos antes de la próxima actualización
            }
        }
    }

    private suspend fun updateDevice(deviceId: String) {
        val device = repository.getDevice(deviceId)
        _uiState.update { it.copy(currentDevice = device as Speaker?) }
    }

    fun turnOn() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Lamp.TURN_ON_ACTION) },
        { state, _ -> state }
    )

    fun turnOff() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, Lamp.TURN_OFF_ACTION) },
        { state, _ -> state }
    )

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (SpeakerUiState, T) -> SpeakerUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { updateState(it, response) } }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (SpeakerUiState, R) -> SpeakerUiState
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