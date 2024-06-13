package com.hci.TP3_HCI.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Card(
    title: String,
    nowPlaying: String,
    isPlaying: Boolean,
    onTogglePlay: (Boolean) -> Unit
) {
    val isChecked = remember { mutableStateOf(isPlaying) }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(Color(0xFF6A1B9A), RoundedCornerShape(12.dp))
            .height(90.dp)
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Now Playing: $nowPlaying",
                        color = Color.White,
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Switch(
                    checked = isChecked.value,
                    onCheckedChange = {
                        isChecked.value = it
                        onTogglePlay(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF00E676),
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = Color.White,
                        uncheckedTrackColor = Color(0xFF9E9E9E)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prevCard() {
    Card(
        title = "My Speaker",
        nowPlaying = "Rock",
        isPlaying = true,
        onTogglePlay = {}
    )
}
