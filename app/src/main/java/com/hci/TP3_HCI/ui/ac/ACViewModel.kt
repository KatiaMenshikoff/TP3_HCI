package com.hci.TP3_HCI.ui.ac

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hci.TP3_HCI.DataSourceException
import com.hci.TP3_HCI.model.AC
import com.hci.TP3_HCI.model.Error
import com.hci.TP3_HCI.model.Lamp
import com.hci.TP3_HCI.model.Speaker
import com.hci.TP3_HCI.repository.DeviceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ACViewModel(
    private val repository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ACUiState())
    val uiState = _uiState.asStateFlow()

    val funSpeeds = arrayOf("auto", "25", "50", "75", "100")
    val verticalSwing = arrayOf("auto", "22", "45", "67", "90")
    val horizontalSwing = arrayOf("auto", "-90", "-45", "0", "45", "90")

    fun setCurrentDevice(deviceId: String){
        runOnViewModelScope(
            { repository.getDevice(deviceId) },
            { state, response -> state.copy(currentDevice = response as AC?) }
        )
    }

    fun startPeriodicUpdates(deviceId: String) {
        viewModelScope.launch {
            while (true) {
                updateDevice(deviceId)
                delay(500)
            }
        }
    }

    private suspend fun updateDevice(deviceId: String) {
        val device = repository.getDevice(deviceId)
        _uiState.update { it.copy(currentDevice = device as AC?) }
    }


    fun turnOn() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, AC.TURN_ON_ACTION) },
        { state, _ -> state }
    )

    fun turnOff() = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, AC.TURN_OFF_ACTION) },
        { state, _ -> state }
    )

    fun setTemperature(temp: Int) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, AC.SET_TEMPERATURE_ACTION, arrayOf(temp)) },
        { state, _ -> state }
    )

    fun setMode(mode: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, AC.SET_MODE_ACTION, arrayOf(mode))},
        { state, _ -> state }
    )

    fun setVerticalSwing(swing: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, AC.SET_VERTICAL_SWING_ACTION, arrayOf(swing))},
        { state, _ -> state }
    )

    fun setHorizontalSwing(swing: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, AC.SET_HORIZONTAL_SWING_ACTION, arrayOf(swing))},
        { state, _ -> state }
    )

      fun setFanSpeed(speed: String) = runOnViewModelScope(
        { repository.executeDeviceAction(uiState.value.currentDevice?.id!!, AC.SET_FAN_SPEED_ACTION, arrayOf(speed))},
        { state, _ -> state }
    )

    fun getNextSpeed(currentSpeed: String): String {
    val index = funSpeeds.indexOf(currentSpeed)
    return if (index == -1 || index == funSpeeds.size - 1) {
        currentSpeed
    } else {
        funSpeeds[index + 1]
    }
    }

    fun getPreviousSpeed(currentSpeed: String): String {
    val index = funSpeeds.indexOf(currentSpeed)
    return if (index <= 0) {
        currentSpeed
    } else {
        funSpeeds[index - 1]
    }
    }


    fun getVerticalNext(currentSwing: String): String {
        val index = verticalSwing.indexOf(currentSwing)
        return if (index == -1 || index == verticalSwing.size - 1) {
            currentSwing
        } else {
            verticalSwing[index + 1]
        }
    }

    fun getVerticalPrevious(currentSwing: String): String {
    val index = verticalSwing.indexOf(currentSwing)
    return if (index <= 0) {
        currentSwing
    } else {
        verticalSwing[index - 1]
    }
    }

    fun getHorizontalNext(currentSwing: String): String {
    val index = horizontalSwing.indexOf(currentSwing)
    return if (index == -1 || index == horizontalSwing.size - 1) {
        currentSwing
    } else {
        horizontalSwing[index + 1]
    }
    }

    fun getHorizontalPrevious(currentSwing: String): String {
        val index = horizontalSwing.indexOf(currentSwing)
        return if (index <= 0) {
            currentSwing
        } else {
            horizontalSwing[index - 1]
        }
    }


    private fun <T> collectOnViewModelScope(
        flow: Flow<T>,
        updateState: (ACUiState, T) -> ACUiState
    ) = viewModelScope.launch {
        flow
            .distinctUntilChanged()
            .catch { e -> _uiState.update { it.copy(error = handleError(e)) } }
            .collect { response -> _uiState.update { updateState(it, response) } }
    }

    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (ACUiState, R) -> ACUiState
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