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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
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
import com.hci.TP3_HCI.ui.getViewModelFactory

@Composable
fun SpeakerScreen(
    deviceId: String,
    viewModel: SpeakerViewModel = viewModel(factory = getViewModelFactory()),
) {

    val uiState by viewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
    var userVolume by remember{mutableStateOf(0f)}

    LaunchedEffect(deviceId) {
        viewModel.setCurrentDevice(deviceId)
        viewModel.startPeriodicUpdates(deviceId) // Iniciar actualizaciones periódicas
        viewModel.getPlaylist()
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 16.dp, 16.dp,  bottom = if(isLandscape) 0.dp else 80.dp)
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


            var playback: String
            if(uiState.currentDevice?.status != SpeakerStatus.STOPPED){
                var genre = uiState.currentDevice?.genre ?: "NO DATA"
                playback = "Now Playing: $genre"
            } else playback = stringResource(id = R.string.not_playing)

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
                if (uiState.currentDevice?.status == SpeakerStatus.STOPPED) {
                    Button(onClick = { viewModel.play() }) {
                        Text(text = stringResource(id = R.string.play))
                    }
                } else {
                    Button(onClick = { viewModel.stop() }) {
                        Text(text = stringResource(id = R.string.stop))
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Playback Controls box
            if (uiState.currentDevice?.status != SpeakerStatus.STOPPED) {
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
                            uiState.currentDevice?.song?.title ?: stringResource(id = R.string.not_playing),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(max = 250.dp) // Adjust max width as needed
                        )
                        Text(
                            uiState.currentDevice?.song?.artist ?: stringResource(id = R.string.not_playing),
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
                                text = stringResource(id = R.string.progress ) + (uiState.currentDevice?.song?.progress
                                    ?:  stringResource(id = R.string.not_playing)),
                                color = Color.White,
                                fontSize = 14.sp
                            )
                            Text(
                                text =  stringResource(id = R.string.duration) + (uiState.currentDevice?.song?.duration
                                    ?:  stringResource(id = R.string.not_playing)),
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))

            // Volume
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
            if (uiState.currentDevice?.status != SpeakerStatus.STOPPED) {
                Text( stringResource(id = R.string.genres), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Column {
                    val genre = uiState.currentDevice?.genre
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(onClick = { viewModel.setGenre("rock") }) {
                            Text( stringResource(id = R.string.rock))
                        }
                        Button(onClick = { viewModel.setGenre("dance") }) {
                            Text( stringResource(id = R.string.dance))
                        }
                        Button(onClick = { viewModel.setGenre("pop") }) {
                            Text( stringResource(id = R.string.pop))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(onClick = { viewModel.setGenre("latina") }) {
                            Text( stringResource(id = R.string.latina))
                        }
                        Button(onClick = { viewModel.setGenre("classical") }) {
                            Text( stringResource(id = R.string.classical))
                        }
                        Button(onClick = { viewModel.setGenre("country") }) {
                            Text( stringResource(id = R.string.country))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Playlist
            if (uiState.currentDevice?.status != SpeakerStatus.STOPPED){
                Text( stringResource(id = R.string.playlist), fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                            text = stringResource(id = R.string.songs_play),
                            color = colorResource(id = R.color.white),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        uiState.playlist?.let { playlist ->
                            playlist.forEach { song ->
                                val songInfo = song as Map<String, String>
                                val songName: String = songInfo["title"]!!
                                val artistTitle: String = songInfo["artist"]!!
                                Text(
                                    text = stringResource(id = R.string.song_info, songName, artistTitle),
                                    color = colorResource(id = R.color.white),
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.widthIn(max = 250.dp)
                                )
                            }
                        } ?: run {
                            Text( stringResource(id = R.string.no_songs))
                        }
                    }
                }
            }


        }
    }
}