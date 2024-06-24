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
                        uiState.currentDevice?.name ?: stringResource(R.string.no_data),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            var genre = uiState.currentDevice?.genre ?: stringResource(R.string.no_data)
            var status = uiState.currentDevice?.status ?: stringResource(R.string.no_data)
            var playback =
                if (genre == null || status == SpeakerStatus.STOPPED) stringResource(R.string.not_playing) else ( stringResource(R.string.now_playing)  + genre)

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
                        Text(text =  stringResource(R.string.play) )
                    }
                } else {
                    Button(onClick = { viewModel.stop() }) {
                        Text(text =  stringResource(R.string.stop) )
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
                            uiState.currentDevice?.song?.title ?:stringResource(R.string.not_playing),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(max = 250.dp) // Adjust max width as needed
                        )
                        Text(
                            uiState.currentDevice?.song?.artist ?: stringResource(R.string.not_playing),
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
                                text = stringResource(R.string.progress) + (uiState.currentDevice?.song?.progress
                                    ?: stringResource(R.string.not_playing)),
                                color = Color.White,
                                fontSize = 14.sp
                            )
                            Text(
                                text =stringResource(R.string.duration) + (uiState.currentDevice?.song?.duration
                                    ?: stringResource(R.string.not_playing)),
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))

            Text(stringResource(R.string.volume), fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                Text(stringResource(R.string.genre), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Column {
                    val genre = uiState.currentDevice?.genre
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(onClick = { viewModel.setGenre("rock") }) {
                            Text(stringResource(R.string.rock))
                        }
                        Button(onClick = { viewModel.setGenre("dance") }) {
                            Text(stringResource(R.string.dance))
                        }
                        Button(onClick = { viewModel.setGenre("pop") }) {
                            Text(stringResource(R.string.pop))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(onClick = { viewModel.setGenre("latina") }) {
                            Text(stringResource(R.string.latina))
                        }
                        Button(onClick = { viewModel.setGenre("classical") }) {
                            Text(stringResource(R.string.classical))
                        }
                        Button(onClick = { viewModel.setGenre("country") }) {
                            Text(stringResource(R.string.country))
                        }
                    }
                }
                }

            //Playlist
            if (status != SpeakerStatus.STOPPED){
                Text(stringResource(R.string.playlist), fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                            text = stringResource(R.string.songs_play),
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
                            Text(stringResource(R.string.no_songs))
                        }
                    }
                }
            }


        }
    }
}

