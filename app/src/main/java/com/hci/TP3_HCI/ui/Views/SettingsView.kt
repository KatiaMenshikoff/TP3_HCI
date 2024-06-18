package com.hci.TP3_HCI.ui.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.SolidColor
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
fun LightDetailScreen(navController: NavHostController) {
    var lightStatus by remember { mutableStateOf(true) }
    var showInHome by remember { mutableStateOf(true) }
    var lightColor by remember { mutableStateOf(Color.Red) }
    var lightIntensity by remember { mutableStateOf(0.5f) }

    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { innerPadding ->
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
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.icon_device), contentDescription = "Light", tint = Color.Black)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("My Light", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(painter = painterResource(id = R.drawable.icon_edit), contentDescription = "Edit", tint = Color.Gray, modifier = Modifier.size(20.dp))
                }
                IconButton(onClick = { /* Handle delete light */ }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.icon_delete), contentDescription = "Delete light", tint = Color.Red, modifier = Modifier.size(30.dp))
                    }
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
                    onCheckedChange = { lightStatus = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = colorResource(id = R.color.pinkMenu),
                        uncheckedThumbColor = colorResource(id = R.color.grey)
                    )
                )
            }

            // Show in home switch
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Show as shortcut in Home", fontSize = 18.sp)
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
                ColorButton(Color.Red, lightColor) { lightColor = it }
                ColorButton(Color.Green, lightColor) { lightColor = it }
                ColorButton(Color.Blue, lightColor) { lightColor = it }
                ColorButton(Color.Yellow, lightColor) { lightColor = it }
                ColorButton(Color.Cyan, lightColor) { lightColor = it }
                ColorButton(Color.Magenta, lightColor) { lightColor = it }
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
                    onValueChange = { lightIntensity = it },
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
fun ColorButton(color: Color, currentColor: Color, onClick: (Color) -> Unit) {
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
            .clickable { onClick(color) }
    )
}

@Preview(showBackground = true)
@Composable
fun LightDetailScreenPreview() {
    LightDetailScreen(navController = rememberNavController())
}
