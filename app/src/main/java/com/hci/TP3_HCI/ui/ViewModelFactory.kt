package com.hci.TP3_HCI.ui

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.hci.TP3_HCI.ApiApplication
import com.hci.TP3_HCI.repository.DeviceRepository

import com.hci.TP3_HCI.ui.devices.DevicesViewModel
import com.hci.TP3_HCI.ui.lamp.LampViewModel
import com.hci.TP3_HCI.ui.ac.ACViewModel
import com.hci.TP3_HCI.ui.sprinkler.SprinklerViewModel
import com.hci.TP3_HCI.ui.speaker.SpeakerViewModel

@Composable
fun getViewModelFactory(defaultArgs: Bundle? = null): ViewModelFactory {
    val application = (LocalContext.current.applicationContext as ApiApplication)
    val deviceRepository = application.deviceRepository
    return ViewModelFactory(
        deviceRepository,
        LocalSavedStateRegistryOwner.current,
        defaultArgs
    )
}

class ViewModelFactory (
    private val deviceRepository: DeviceRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(DevicesViewModel::class.java) ->
                DevicesViewModel(deviceRepository)

            isAssignableFrom(LampViewModel::class.java) ->
                LampViewModel(deviceRepository)

            isAssignableFrom(ACViewModel::class.java) ->
                ACViewModel(deviceRepository)

            isAssignableFrom(SpeakerViewModel::class.java) ->
                SpeakerViewModel(deviceRepository)

            isAssignableFrom(SprinklerViewModel::class.java) ->
                SprinklerViewModel(deviceRepository)

            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}