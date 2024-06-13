package com.hci.TP3_HCI.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.ui.theme.pinkMenu

@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Devices", "Automation", "Settings")

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = {
                            val icon = when (item) {
                                "Home" -> painterResource(id = R.drawable.icon_home)
                                "Devices" -> painterResource(id = R.drawable.icon_device)
                                "Automation" -> painterResource(id = R.drawable.icon_automation)
                                "Settings" -> painterResource(id = R.drawable.icon_settings)
                                else -> painterResource(id = R.drawable.icon_home)
                            }
                            Icon(
                                painter = icon,
                                contentDescription = null,
                                tint = if (selectedItem == index) Color.Gray else Color.Gray
                            )
                        },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier.background(if (selectedItem == index) pinkMenu else Color.Transparent)
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Home") { Text("Home Screen", modifier = Modifier.padding(16.dp)) }
            composable("Devices") { Text("Devices Screen", modifier = Modifier.padding(16.dp)) }
            composable("Automations") { Text("Automations Screen", modifier = Modifier.padding(16.dp)) }
            composable("Settings") { Text("Settings Screen", modifier = Modifier.padding(16.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}
