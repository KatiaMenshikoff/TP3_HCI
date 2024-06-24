package com.hci.TP3_HCI.ui.lamp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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

    // Predefined list of colors
    val predefinedColors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta)

    // Initialize lightColor based on current device color
    var lightColor by remember {
        mutableStateOf(predefinedColors.find { it.toHex() == uiState.currentDevice?.color } ?: Color.Red)
    }
    var lightStatus by remember { mutableStateOf(uiState.currentDevice?.status == Status.ON) }
    var lightIntensity by remember { mutableStateOf(50f) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(R.color.background))
                .padding(16.dp)
        ) {
            // Top bar with navigation and actions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.icon_lamp), contentDescription = "Light", tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(uiState.currentDevice?.name ?: "My Light", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }

            Divider()

            Text("Options", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))

            // Light status switch
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Light Status (on/off)", fontSize = 18.sp)
                Switch(
                    checked = lightStatus,
                    onCheckedChange = {
                        lightStatus = it
                        if (it) {
                            viewModel.turnOn()
                        } else {
                            viewModel.turnOff()
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(id = R.color.pinkMenu),
                        uncheckedThumbColor = colorResource(id = R.color.grey)
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Light image with changing background color
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(color = lightColor, shape = RoundedCornerShape(12.dp)),
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
                    ColorButton(color, lightColor, viewModel::setColor) { lightColor = it }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Light intensity slider
            Text("Brightness", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
                        viewModel.setBrightness(it) // Call setBrightness with the new value
                    },
                    valueRange = 0f..100f, // Set the range for the slider
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
                color = if (color == currentColor) colorResource(id = R.color.pinkMenu) else Color.Transparent,
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
