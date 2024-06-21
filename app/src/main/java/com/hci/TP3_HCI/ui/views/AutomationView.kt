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
import com.hci.TP3_HCI.ui.Components.SprinklersCard
import com.hci.TP3_HCI.ui.Components.BottomNavigationBar

@Composable
fun AutomationsScreen(navController: NavHostController) {
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
            Text("Automations", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(listOf("Sprinklers", "Vacuum", "Close Windows")) { automation ->
                    SprinklersCard(
                        title = automation,
                        time = "Scheduled",
                        actions = "2 actions"
                    )
                }
            }
            Button(
                onClick = { /* Handle add new automation */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.button), // Color del botón
                    contentColor = colorResource(id = R.color.white) // Color del texto del botón
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("New Automation")
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun AutomationsScreenPreview() {
    val navController = rememberNavController()
    AutomationsScreen(navController)
}