package com.hci.TP3_HCI.ui.sprinkler

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.ui.getViewModelFactory
import com.hci.TP3_HCI.ui.lamp.LampViewModel

@Composable
fun SprinklerScreen(
    deviceId: String,
    viewModel: SprinklerViewModel = viewModel(factory = getViewModelFactory()),
) {

    LaunchedEffect(deviceId) {
        viewModel.setCurrentDevice(deviceId)
        viewModel.startPeriodicUpdates(deviceId) // Iniciar actualizaciones periódicas
    }

    val uiState by viewModel.uiState.collectAsState()
    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(R.color.background))
                .padding(3.dp)
        ) {
            val currentDeviceData = uiState.currentDevice?.let {
                "(${it.id}) ${it.name}"
            } ?: stringResource(R.string.unknown)
            Text(
                text = stringResource(R.string.current_device, currentDeviceData),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
