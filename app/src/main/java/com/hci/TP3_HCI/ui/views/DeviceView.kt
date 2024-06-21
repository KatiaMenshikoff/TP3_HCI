package com.hci.TP3_HCI.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.ui.Components.CustomCard
import com.hci.TP3_HCI.ui.Components.BottomNavigationBar

@Composable
fun DevicesScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(R.color.background))
                .padding(3.dp)
        ) {
            Text("Devices ", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(listOf("My Speaker", "My Roomba", "My Light")) { device ->
                    CustomCard(
                        title = device,
                        description = "Status",
                        isPlaying = false,
                        onTogglePlay = { /* Handle toggle */ }
                    )
                }
            }
            Button(
                onClick = { /* Handle add new device */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.button), // Color del botón
                    contentColor = colorResource(id = R.color.white) // Color del texto del botón
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("New Device")
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun DevicesScreenPreview() {
    val navController = rememberNavController()
    DevicesScreen(navController)
}