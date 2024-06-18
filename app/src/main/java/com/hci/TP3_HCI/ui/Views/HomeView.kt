package com.hci.TP3_HCI.ui.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hci.TP3_HCI.ui.Components.CustomCard
import com.hci.TP3_HCI.ui.Components.BottomNavigationBar
import com.hci.TP3_HCI.R
@Composable
fun HomeView(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(R.color.background))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome! 😃", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
            CustomCard(
                title = "Home Security",
                description = "On",
                isPlaying = false,
                onTogglePlay = { /* Handle toggle */ }
            )
            CustomCard(
                title = "Air Conditioning",
                description = "24°C",
                isPlaying = false,
                onTogglePlay = { /* Handle toggle */ }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { /* Handle toggle lights */ }) {
                Text("Toggle Lights")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Active Devices")
            // List of active devices (can be implemented similarly using CustomCard)
        }
    }
}
