package com.hci.TP3_HCI.ui.devices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.ui.component.DeviceCard
import com.hci.TP3_HCI.ui.getViewModelFactory
import com.hci.TP3_HCI.ui.lamp.LampViewModel
import com.hci.TP3_HCI.model.DeviceType


@Composable
fun DevicesScreen(
    onNavigateToLamp: (deviceId: String) -> Unit,
    onNavigateToAC: (deviceId: String) -> Unit,
    onNavigateToSpeaker: (deviceId: String) -> Unit,
    onNavigateToSprinkler: (deviceId: String) -> Unit,
    viewModel: DevicesViewModel = viewModel(factory = getViewModelFactory()),
) {
    val uiState by viewModel.uiState.collectAsState()

    val deviceScreens = mapOf(
        DeviceType.LAMP to onNavigateToLamp,
        DeviceType.AC to onNavigateToAC,
        DeviceType.SPEAKER to onNavigateToSpeaker,
        DeviceType.SPRINKLER to onNavigateToSprinkler
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.Devices),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_device),
                contentDescription = "Speaker Icon",
                tint = Color.Gray,
                modifier = Modifier.size(40.dp)
            )
        }
        
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 196.dp),
            modifier = Modifier.padding(start = 16.dp, bottom = 70.dp, end = 16.dp)
        ) {
            items(
                count = uiState.devices.size,
                key = { index ->
                    uiState.devices[index].id!!
                }
            ) { index ->
                val device = uiState.devices[index]
                DeviceCard(
                    device = uiState.devices[index],
                    onClick = { deviceScreens[device.type]?.invoke(device.id!!) }
                )
            }
        }
    }
}


