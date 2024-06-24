package com.hci.TP3_HCI.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.model.Device
import com.hci.TP3_HCI.model.DeviceType

@Composable
fun DeviceCard(
    device: Device,
    onClick: () -> Unit,
) {
    val deviceTypeNames = mapOf(
        DeviceType.LAMP to R.string.views_lamp_name,
        DeviceType.AC to R.string.views_ac_name,
        DeviceType.SPEAKER to R.string.views_speaker_name,
        DeviceType.SPRINKLER to R.string.views_sprinkler_name
    )
    val devicesIcons = mapOf(
        DeviceType.LAMP to R.drawable.icon_lamp,
        DeviceType.AC to R.drawable.icon_ac,
        DeviceType.SPEAKER to R.drawable.icon_speaker,
        DeviceType.SPRINKLER to R.drawable.icon_sprinkler
    )
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.device)),
        modifier = Modifier
            .padding(16.dp)
            .height(100.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
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
                    text = device.name,
                    color = Color.White,
                    fontSize = 19.sp,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = deviceTypeNames[device.type] ?: R.string.unknown),
                    color = Color.White,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = devicesIcons[device.type] ?: R.drawable.icon_device), // Replace with your icon resource
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun DeviceCardPreview() {
    
}
