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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.model.SpeakerStatus
import com.hci.TP3_HCI.model.Status
import com.hci.TP3_HCI.ui.getViewModelFactory
import com.hci.TP3_HCI.ui.screens.SongItem

@Composable
fun SpeakerScreen(
    deviceId: String,
    viewModel: SpeakerViewModel = viewModel(factory = getViewModelFactory()),
) {

    val uiState by viewModel.uiState.collectAsState()

    var userVolume by remember{mutableStateOf(0f)}

    LaunchedEffect(deviceId) {
        viewModel.setCurrentDevice(deviceId)
        viewModel.startPeriodicUpdates(deviceId) // Iniciar actualizaciones periódicas
        viewModel.getPlaylist()
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .padding(16.dp, 16.dp, 16.dp, bottom = 80.dp)
    ) {
        item {

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
                Icon(painter = painterResource(id = R.drawable.icon_speaker), contentDescription = "Speaker Icon",
                    tint = Color.Gray, modifier = Modifier.size(40.dp))
            }


            var genre = uiState.currentDevice?.genre ?: "NO DATA"
            var status = uiState.currentDevice?.status ?: "NO DATA"
            var playback =
                if (genre == null || status == SpeakerStatus.STOPPED) "Not playing" else ("Now playing " + genre)

            // Playback
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$playback",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                if (status == SpeakerStatus.STOPPED) {
                    Button(onClick = { viewModel.play() }) {
                        Text(text = "PLAY")
                    }
                } else {
                    Button(onClick = { viewModel.stop() }) {
                        Text(text = "STOP")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Playback Controls box
            if (status != SpeakerStatus.STOPPED) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.dp)
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
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(max = 250.dp) // Adjust max width as needed
                        )
                        Text(
                            uiState.currentDevice?.song?.artist ?: "Not playing",
                            color = Color.White,
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(max = 250.dp) // Adjust max width as needed
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Button(onClick = { viewModel.previousSong() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_previous),
                                    contentDescription = "Previous",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(24.dp))
                            Button(onClick = {
                                if (uiState.currentDevice?.status == SpeakerStatus.PLAYING) {
                                    viewModel.pause()
                                } else {
                                    viewModel.resume()
                                }
                            }) {
                                Icon(
                                    painter = if (uiState.currentDevice?.status == SpeakerStatus.PLAYING) {
                                        painterResource(id = R.drawable.icon_pause)
                                    } else {
                                        painterResource(id = R.drawable.icon_play)
                                    },
                                    contentDescription = "Playback",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(24.dp))
                            Button(onClick = { viewModel.nextSong() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_next),
                                    contentDescription = "Next",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
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
                    value = userVolume,
                    onValueChangeFinished = { viewModel.setVolume(userVolume * 10f) },
                    onValueChange = { newVolume ->
                        userVolume =
                            (newVolume) // Multiplica por 10 para ajustarlo a la escala correcta
                    },
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

            // Genre picker
            if (status != SpeakerStatus.STOPPED) {
                Text("Genres", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Column {
                    val genre = uiState.currentDevice?.genre
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(onClick = { viewModel.setGenre("rock") }) {
                            Text("Rock")
                        }
                        Button(onClick = { viewModel.setGenre("dance") }) {
                            Text("Dance")
                        }
                        Button(onClick = { viewModel.setGenre("pop") }) {
                            Text("Pop")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(onClick = { viewModel.setGenre("latina") }) {
                            Text("Latina")
                        }
                        Button(onClick = { viewModel.setGenre("classical") }) {
                            Text("Classical")
                        }
                        Button(onClick = { viewModel.setGenre("country") }) {
                            Text("Country")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Playlist
            if (status != SpeakerStatus.STOPPED){
                Text("Playlist", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.dp)
                        .background(
                            color = colorResource(id = R.color.button),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "Songs in playlist",
                            color = colorResource(id = R.color.white),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        uiState.playlist?.let { playlist ->
                            playlist.forEach { song ->
                                val songInfo = song as Map<String, String>
                                //TODO traduccion a espaniol de la palabra "by"
                                Text(
                                    text = "${songInfo["title"]}" + " by " + "${songInfo["artist"]}",
                                    color = colorResource(id = R.color.white),
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.widthIn(max = 250.dp)
                                )
                            }
                        } ?: run {
                            Text("No songs in playlist.")
                        }
                    }
                }
            }


        }
    }
}
