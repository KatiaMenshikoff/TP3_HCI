package com.hci.TP3_HCI.ui.Components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Search", "Profile")

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color(0xFF6200EA)
            ) {
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = when (item) {
                                    "Home" -> Icons.Default.Home
                                    "Search" -> Icons.Default.Search
                                    "Profile" -> Icons.Default.Person
                                    else -> Icons.Default.Home
                                },
                                contentDescription = null,
                                tint = if (selectedItem == index) Color.White else Color.Gray
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
                        }
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
            composable("Search") { Text("Search Screen", modifier = Modifier.padding(16.dp)) }
            composable("Profile") { Text("Profile Screen", modifier = Modifier.padding(16.dp)) }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}
