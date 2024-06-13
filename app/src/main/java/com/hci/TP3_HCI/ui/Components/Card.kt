package com.hci.TP3_HCI.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.ui.theme.device  

@Composable
fun Card(
    title: String,
    description: String,
    isPlaying: Boolean,
    onTogglePlay: (Boolean) -> Unit
) {
    val isChecked = remember { mutableStateOf(isPlaying) }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(device, RoundedCornerShape(12.dp))
            .height(100.dp)
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_play),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = description,
                        color = Color.Black,
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_device),
                    contentDescription = null,
                    tint = Color.Black,
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
        title = "Title Cambiable",
        description = "Esto es la descripcion",
        isPlaying = true,
        onTogglePlay = {}
    )
}
