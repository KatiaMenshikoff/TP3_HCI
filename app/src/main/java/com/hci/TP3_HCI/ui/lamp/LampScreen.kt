package com.hci.TP3_HCI.ui.lamp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.model.Status
import com.hci.TP3_HCI.ui.getViewModelFactory

@Composable
fun LampScreen(
    deviceId: String,
    viewModel: LampViewModel = viewModel(factory = getViewModelFactory()),
) {
    LaunchedEffect(deviceId) {
        viewModel.setCurrentDevice(deviceId)
        viewModel.startPeriodicUpdates(deviceId) // Iniciar actualizaciones periódicas
    }

    val uiState by viewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE


    val predefinedColors = listOf(Color.Red, Color.Green, Color.White, Color.Yellow, Color.Cyan, Color.Magenta)

    var lightColor by remember {
        mutableStateOf(predefinedColors.find { it.toHex() == uiState.currentDevice?.color } ?: Color.Red)
    }
    var lightIntensity by remember { mutableStateOf(50f) }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 16.dp, 16.dp,  bottom = if(isLandscape) 0.dp else 80.dp)
        ) {
            item{
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
                Icon(painter = painterResource(id = R.drawable.icon_lamp), contentDescription = "Lamp Icon",
                    tint = Color.Gray, modifier = Modifier.size(40.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.status), fontSize = 18.sp)
                Switch(
                    checked = uiState.currentDevice?.status == Status.ON,
                    onCheckedChange = {
                        if (uiState.currentDevice?.status == Status.ON) {
                            viewModel.turnOff()
                        } else {
                            viewModel.turnOn()
                        }
                    },
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
                    .height(120.dp)
                    .background(color = predefinedColors.find { it.toHex() == uiState.currentDevice?.color } ?: Color.Red, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_lamp),
                    contentDescription = "Lamp",
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Light color selection
            Text("Color", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                predefinedColors.forEach { color ->
                    ColorButton(color, predefinedColors.find { it.toHex() == uiState.currentDevice?.color } ?: Color.Red, viewModel::setColor) { lightColor = it }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Light intensity slider
            Text(stringResource(R.string.brightness), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.icon_minus), contentDescription = "Low brightness", tint = Color.Gray)
                Slider(
                    value = lightIntensity,
                    onValueChange = {
                        lightIntensity = it
                        viewModel.setBrightness(it)
                    },
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        thumbColor = colorResource(id = R.color.pinkMenu),
                        activeTrackColor = colorResource(id = R.color.button),
                        inactiveTrackColor = colorResource(id = R.color.grey)
                    ),
                    modifier = Modifier.weight(1f)
                )
                Icon(painter = painterResource(id = R.drawable.icon_more), contentDescription = "High brightness", tint = Color.Gray)
            }
        }
    }
}

@Composable
fun ColorButton(color: Color, currentColor: Color, setColor: (String) -> Unit, onClick: (Color) -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .padding(horizontal = 4.dp)
            .background(color = color, shape = RoundedCornerShape(12.dp))
            .border(
                width = 2.dp,
                color = if (color == currentColor) Color.DarkGray else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                setColor(color.toHex())
                onClick(color)
            }
    )
}

fun Color.toHex(): String {
    val intColor = this.toArgb()
    return String.format("%06X", 0xFFFFFF and intColor)
}
