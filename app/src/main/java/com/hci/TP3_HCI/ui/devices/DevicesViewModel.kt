package com.hci.TP3_HCI.ui.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hci.TP3_HCI.DataSourceException
import com.hci.TP3_HCI.repository.DeviceRepository
import com.hci.TP3_HCI.model.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DevicesViewModel(
    private val repository: DeviceRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DevicesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        collectOnViewModelScope(
            repository.devices
        ) { state, response -> state.copy(devices = response) }
    }

    fun setCurrentDeviceId(deviceId: String?) {
        repository.setCurrentDeviceId(deviceId)
    }

    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (DevicesUiState, T) -> DevicesUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { updateState(it, response) } }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }
}