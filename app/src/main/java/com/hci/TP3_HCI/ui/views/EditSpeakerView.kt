package com.hci.TP3_HCI.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.ui.Components.BottomNavigationBar

@Composable
fun SpeakerDetailScreen(navController: NavHostController) {
    var deviceStatus by remember { mutableStateOf(true) }
    var showInHome by remember { mutableStateOf(true) }
    var currentGenre by remember { mutableStateOf("Rock") }
    var currentTime by remember { mutableStateOf(1.13f) } // tiempo actual en minutos
    val totalTime = 3.12f // tiempo total de la canción en minutos

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
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
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
                    checked = deviceStatus,
                    onCheckedChange = { deviceStatus = it },
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
                    checked = showInHome,
                    onCheckedChange = { showInHome = it },
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
                    GenreButton("Rock", currentGenre) { currentGenre = it }
                    GenreButton("Jazz", currentGenre) { currentGenre = it }
                    GenreButton("Pop", currentGenre) { currentGenre = it }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    GenreButton("Latina", currentGenre) { currentGenre = it }
                    GenreButton("Classic", currentGenre) { currentGenre = it }
                    GenreButton("Country", currentGenre) { currentGenre = it }
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

@Composable
fun GenreButton(genre: String, currentGenre: String, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(genre) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (genre == currentGenre) colorResource(id = R.color.pinkButton) else Color.LightGray,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Text(genre, fontSize = 16.sp)
    }
}

@Composable
fun SongItem(song: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.icon_play), contentDescription = "Music note", tint = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(song, fontSize = 18.sp)
    }
}

@Composable
@Preview(showBackground = true)
fun SpeakerDetailScreenPreview() {
    SpeakerDetailScreen(navController = rememberNavController())
}

