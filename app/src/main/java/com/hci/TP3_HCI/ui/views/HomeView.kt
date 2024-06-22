package com.hci.TP3_HCI.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.hci.TP3_HCI.ui.component.DeviceCard
import com.hci.TP3_HCI.R

@Composable
fun HomeScreen() {
    Scaffold() { paddingValues ->
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
