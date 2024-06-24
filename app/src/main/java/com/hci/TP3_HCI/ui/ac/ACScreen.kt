package com.hci.TP3_HCI.ui.ac

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.model.Status
import com.hci.TP3_HCI.ui.getViewModelFactory

@Composable
fun ACScreen(
    deviceId: String,
    viewModel: ACViewModel = viewModel(factory = getViewModelFactory()),
) {

    LaunchedEffect(deviceId) {
        viewModel.setCurrentDevice(deviceId)
        viewModel.startPeriodicUpdates(deviceId) // Iniciar actualizaciones periódicas
    }
    val uiState by viewModel.uiState.collectAsState()
    var ACStatus by remember { mutableStateOf(uiState.currentDevice?.status == Status.ON) }
    var selectedMode by remember { mutableStateOf(uiState.currentDevice?.mode ?: "cool") }
    var temperature by remember { mutableStateOf(24) }
    var fanSpeed by remember { mutableStateOf("auto") }
    var hSwing by remember { mutableStateOf("auto") }
    var vSwing by remember { mutableStateOf("auto") }
    var showInHome by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(R.color.background))
                .padding(16.dp)
        ) {
            // Device Name and Status
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Title
                Text(
                    text = uiState.currentDevice?.name ?: stringResource(id = R.string.no_data),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                )
                Icon(painter = painterResource(id = R.drawable.icon_ac), contentDescription = "AC Icon",
                    tint = Color.Gray, modifier = Modifier.size(40.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.status), fontSize = 18.sp)
                Switch(
                    checked = ACStatus,
                    onCheckedChange = {
                        ACStatus = it
                        if (it) {
                            viewModel.turnOn()
                        } else {
                            viewModel.turnOff()
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(id = R.color.pinkMenu),
                        uncheckedThumbColor = colorResource(id = R.color.grey)
                    )
                )
            }

            // Temperature Controls
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    var newTemp = uiState.currentDevice?.temperature ?: 25
                    if (newTemp > 18) {
                        newTemp -= 1
                    }
                    viewModel.setTemperature(newTemp)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_minus),
                        contentDescription = stringResource(id = R.string.decrease)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${uiState.currentDevice?.temperature?.toString() ?: "-"}  °C",
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    var newTemp = uiState.currentDevice?.temperature ?: 25
                    if (newTemp < 38) {
                        newTemp += 1
                    }
                    viewModel.setTemperature(newTemp)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_more),
                        contentDescription = stringResource(id = R.string.increase)
                    )
                }
            }

            // AC Modes
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        selectedMode = "cool"
                        viewModel.setMode("cool")
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (selectedMode == "cool") Color.Blue else Color.Gray
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.cool_mode),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                TextButton(
                    onClick = {
                        selectedMode = "fan"
                        viewModel.setMode("fan")
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (selectedMode == "fan") Color.Blue else Color.Gray
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.fan_mode),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                TextButton(
                    onClick = {
                        selectedMode = "heat"
                        viewModel.setMode("heat")
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (selectedMode == "heat") Color.Blue else Color.Gray
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.heat_mode),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Fan Speed Control
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.fan_speed), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Row {
                    IconButton(onClick = {
                        val newSpeed =
                            viewModel.getPreviousSpeed(uiState.currentDevice?.fanSpeed ?: "auto")
                        viewModel.setFanSpeed(newSpeed)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_minus),
                            contentDescription = stringResource(id = R.string.decrease)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${uiState.currentDevice?.fanSpeed?.toString() ?: "-"}",
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        val newSpeed =
                            viewModel.getNextSpeed(uiState.currentDevice?.fanSpeed ?: "auto")
                        viewModel.setFanSpeed(newSpeed)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_more),
                            contentDescription = stringResource(id = R.string.increase)
                        )
                    }
                }
            }

            // H-Swing Control
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.h_swing), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Row {
                    IconButton(onClick = {
                        val newSwing = viewModel.getHorizontalPrevious(
                            uiState.currentDevice?.horizontalSwing ?: "auto"
                        )
                        viewModel.setHorizontalSwing(newSwing)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_minus),
                            contentDescription = stringResource(id = R.string.decrease)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${uiState.currentDevice?.horizontalSwing?.toString() ?: "-"}",
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        val newSwing = viewModel.getHorizontalNext(
                            uiState.currentDevice?.horizontalSwing ?: "auto"
                        )
                        viewModel.setHorizontalSwing(newSwing)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_more),
                            contentDescription = stringResource(id = R.string.increase)
                        )
                    }
                }
            }

            // V-Swing Control
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.v_swing), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Row {
                    IconButton(onClick = {
                        val newSwing = viewModel.getVerticalPrevious(
                            uiState.currentDevice?.verticalSwing ?: "auto"
                        )
                        viewModel.setVerticalSwing(newSwing)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_minus),
                            contentDescription = stringResource(id = R.string.decrease)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${uiState.currentDevice?.verticalSwing?.toString() ?: "-"}",
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        val newSwing = viewModel.getVerticalNext(
                            uiState.currentDevice?.verticalSwing ?: "auto"
                        )
                        viewModel.setVerticalSwing(newSwing)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_more),
                            contentDescription = stringResource(id = R.string.increase)
                        )
                    }
                }
            }
        }
    }
}