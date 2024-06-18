package com.hci.TP3_HCI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hci.TP3_HCI.ui.Components.BottomNavigationBar
import com.hci.TP3_HCI.ui.Components.SprinklersCard
import com.hci.TP3_HCI.ui.theme.CoolHomeTheme
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.ui.Views.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        composable("Home") { HomeView(navController) }
        composable("Devices") { DevicesScreen(navController) }
        composable("Automations") { AutomationsScreen(navController) }
    }
}
