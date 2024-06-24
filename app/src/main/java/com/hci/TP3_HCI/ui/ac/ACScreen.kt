package com.hci.TP3_HCI.ui.ac

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hci.TP3_HCI.R

@Composable
fun ACScreen(
    deviceId: String,
) {
    var temperature by remember { mutableStateOf(24) }
    var fanSpeed by remember { mutableStateOf("auto") }
    var hSwing by remember { mutableStateOf("auto") }
    var vSwing by remember { mutableStateOf("auto") }
    var showInHome by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->

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
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = stringResource(id = R.string.ac_title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Device Name and Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "mi aire", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Switch(
                    checked = true,
                    onCheckedChange = { /* handle on/off */ },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(R.color.grey),
                        uncheckedThumbColor = colorResource(R.color.grey)
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
                IconButton(onClick = { if (temperature > 16) temperature-- }) {
                    Icon(painter = painterResource(id = R.drawable.icon_minus), contentDescription = stringResource(id = R.string.decrease))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "$temperature°C", fontSize = 32.sp)
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { if (temperature < 30) temperature++ }) {
                    Icon(painter = painterResource(id = R.drawable.icon_more), contentDescription = stringResource(id = R.string.increase))
                }
            }

            // AC Modes
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { /* handle cool mode */ }) {
                    Text(text = stringResource(id = R.string.cool_mode), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                TextButton(onClick = { /* handle fan mode */ }) {
                    Text(text = stringResource(id = R.string.fan_mode), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                TextButton(onClick = { /* handle heat mode */ }) {
                    Text(text = stringResource(id = R.string.heat_mode), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Fan Speed Control
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.fan_speed), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Row {
                    IconButton(onClick = { /* handle decrease fan speed */ }) {
                        Icon(painter = painterResource(id = R.drawable.icon_minus), contentDescription = stringResource(id = R.string.decrease))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = fanSpeed, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { /* handle increase fan speed */ }) {
                        Icon(painter = painterResource(id = R.drawable.icon_more), contentDescription = stringResource(id = R.string.increase))
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
                Text(text = stringResource(id = R.string.h_swing), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Row {
                    IconButton(onClick = { /* handle decrease h-swing */ }) {
                        Icon(painter = painterResource(id = R.drawable.icon_minus), contentDescription = stringResource(id = R.string.decrease))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = hSwing, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { /* handle increase h-swing */ }) {
                        Icon(painter = painterResource(id = R.drawable.icon_more), contentDescription = stringResource(id = R.string.increase))
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
                Text(text = stringResource(id = R.string.v_swing), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Row {
                    IconButton(onClick = { /* handle decrease v-swing */ }) {
                        Icon(painter = painterResource(id = R.drawable.icon_minus), contentDescription = stringResource(id = R.string.decrease))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = vSwing, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = { /* handle increase v-swing */ }) {
                        Icon(painter = painterResource(id = R.drawable.icon_more), contentDescription = stringResource(id = R.string.increase))
                    }
                }
            }

            // Show in Home
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = showInHome,
                    onCheckedChange = { showInHome = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.show_in_home), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            // Delete Button
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { /* handle delete */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.button)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(painter = painterResource(id = R.drawable.icon_delete), contentDescription = stringResource(id = R.string.delete), tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.delete), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ACScreenPreview() {
    ACScreen(
        deviceId = "1",
    )
}
