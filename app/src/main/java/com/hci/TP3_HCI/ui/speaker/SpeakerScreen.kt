package com.hci.TP3_HCI.ui.speaker

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.model.Status
import com.hci.TP3_HCI.ui.getViewModelFactory
import com.hci.TP3_HCI.ui.screens.SongItem

@Composable
fun SpeakerScreen(
    deviceId: String,
    viewModel: SpeakerViewModel = viewModel(factory = getViewModelFactory()),
) {

    LaunchedEffect(deviceId) {
        viewModel.setCurrentDevice(deviceId)
        viewModel.startPeriodicUpdates(deviceId) // Iniciar actualizaciones periódicas
    }

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_device),
                    contentDescription = "Speaker",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    uiState.currentDevice?.name ?: "NO DATA",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = { /* Handle delete device */ }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Delete device", color = Color.Red, fontSize = 12.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.icon_delete),
                        contentDescription = "Delete device",
                        tint = Color.Red,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        Divider()

        Text(
            "Options",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Device Status (on/off)", fontSize = 18.sp)
            Switch(
                checked = uiState.currentDevice?.status == Status.ON,
                //TODO implementar accion
                onCheckedChange = null,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(id = R.color.pinkMenu),
                    uncheckedThumbColor = colorResource(id = R.color.grey)
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Show as shortcut in Home", fontSize = 18.sp)
            Switch(
                //TODO implementar valor
                checked = false,
                //TODO implementar accion
                onCheckedChange = null,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(id = R.color.pinkMenu),
                    uncheckedThumbColor = colorResource(id = R.color.grey)
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    color = colorResource(id = R.color.button),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    uiState.currentDevice?.song?.title ?: "Not playing",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    uiState.currentDevice?.song?.artist ?: "Not playing",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_previous),
                        contentDescription = "Previous",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.icon_play),
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.icon_next),
                        contentDescription = "Next",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Progress:   " + (uiState.currentDevice?.song?.progress
                            ?: "Not playing"),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Duration:   " + (uiState.currentDevice?.song?.duration
                            ?: "Not playing"),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text("Volume", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_volume),
                contentDescription = "Volume down",
                tint = Color.Gray
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_minus),
                contentDescription = "Volume down",
                tint = Color.Gray
            )
            Slider(
                value = (uiState.currentDevice?.volume ?: 0f) / 10f,
                onValueChange = { /* Handle volume change */ },
                colors = SliderDefaults.colors(
                    thumbColor = colorResource(id = R.color.grey),
                    activeTrackColor = colorResource(id = R.color.button),
                    inactiveTrackColor = colorResource(id = R.color.grey)
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_more),
                contentDescription = "Volume up",
                tint = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Genres", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        Column {
            val genre = uiState.currentDevice?.genre
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { }) {
                    Text("Rock")
                }
                Button(onClick = { }) {
                    Text("Jazz")
                }
                Button(onClick = { }) {
                    Text("Pop")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { }) {
                    Text("Latina")
                }
                Button(onClick = { }) {
                    Text("Classic")
                }
                Button(onClick = { }) {
                    Text("Country")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text("Songs", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                SongItem("La Bestia Pop")
                SongItem("De Música Ligera")
                SongItem("Campanas en la noche")
            }
        }
    }
}
