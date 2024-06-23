package com.hci.TP3_HCI.ui.speaker

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.model.Status
import com.hci.TP3_HCI.ui.component.BottomNavigationBar
import com.hci.TP3_HCI.ui.getViewModelFactory
import com.hci.TP3_HCI.ui.lamp.LampViewModel
import com.hci.TP3_HCI.ui.screens.GenreButton
import com.hci.TP3_HCI.ui.screens.SongItem

@Composable
fun SpeakerScreen(
    deviceId: String,
    viewModel: SpeakerViewModel = viewModel(factory = getViewModelFactory()),
) {
    viewModel.setCurrentDevice(deviceId)
    val uiState by viewModel.uiState.collectAsState()

//    val currentDeviceData = uiState.currentDevice?.let {
//        "(${it.id}) ${it.name}"
//    } ?: stringResource(R.string.unknown)

    var status by remember { mutableStateOf<Status?>(null) }
    var genre by remember { mutableStateOf<String?>("NO GENRE") }
    var volume by remember { mutableStateOf<Int?>(0) }

    LaunchedEffect(uiState.currentDevice) {
        uiState.currentDevice?.let { device ->
            volume = device.volume
            genre = device.genre
            status = device.status
        }
    }

    Scaffold (
        bottomBar = { BottomNavigationBar() }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                    Icon(painter = painterResource(id = R.drawable.icon_device), contentDescription = "Speaker", tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("My Speaker", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(painter = painterResource(id = R.drawable.icon_edit), contentDescription = "Edit", tint = Color.Gray, modifier = Modifier.size(20.dp))
                }
                IconButton(onClick = { /* Handle delete device */ }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
//                        Text("Delete device", color = Color.Red, fontSize = 12.sp)
                        Icon(painter = painterResource(id = R.drawable.icon_delete), contentDescription = "Delete device", tint = Color.Red, modifier = Modifier.size(30.dp))
                    }
                }
            }

            Divider()

            Text("Options", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Device Status (on/off)", fontSize = 18.sp)
                Switch(
                    checked = status == Status.ON,
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
                Text("Show as shortcut in Home", fontSize                = 18.sp)
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
                    .background(color = colorResource(id = R.color.button), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("La Bestia Pop", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Patricio Rey y sus Redonditos de ricota", color = Color.White, fontSize = 18.sp)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.icon_previous), contentDescription = "Previous", tint = Color.White, modifier = Modifier.size(30.dp))
                        Spacer(modifier = Modifier.width(24.dp))
                        Icon(painter = painterResource(id = R.drawable.icon_play), contentDescription = "Play", tint = Color.White, modifier = Modifier.size(30.dp))
                        Spacer(modifier = Modifier.width(24.dp))
                        Icon(painter = painterResource(id = R.drawable.icon_next), contentDescription = "Next", tint = Color.White, modifier = Modifier.size(30.dp))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = String.format("%.2f", currentTime),
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        Slider(
                            value = currentTime,
                            onValueChange = { currentTime = it },
                            valueRange = 0f..totalTime,
                            colors = SliderDefaults.colors(
                                thumbColor = colorResource(id = R.color.grey),
                                activeTrackColor = colorResource(id = R.color.pinkMenu),
                                inactiveTrackColor = Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = String.format("%.2f", totalTime),
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
                Icon(painter = painterResource(id = R.drawable.icon_volume), contentDescription = "Volume down", tint = Color.Gray)
                Icon(painter = painterResource(id = R.drawable.icon_minus), contentDescription = "Volume down", tint = Color.Gray)
                Slider(
                    value = 0.5f,
                    onValueChange = { /* Handle volume change */ },
                    colors = SliderDefaults.colors(
                        thumbColor = colorResource(id = R.color.grey),
                        activeTrackColor = colorResource(id = R.color.button),
                        inactiveTrackColor = colorResource(id = R.color.grey)
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(painter = painterResource(id = R.drawable.icon_more), contentDescription = "Volume up", tint = Color.Gray)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Genres", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    GenreButton("Rock", genre!!) { genre = it }
                    GenreButton("Jazz", genre!!) { genre = it }
                    GenreButton("Pop", genre!!) { genre = it }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    GenreButton("Latina", genre!!) { genre = it }
                    GenreButton("Classic", genre!!) { genre = it }
                    GenreButton("Country", genre!!) { genre = it }
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
