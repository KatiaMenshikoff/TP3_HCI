package com.hci.TP3_HCI.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hci.TP3_HCI.ui.views.HomeScreen
import com.hci.TP3_HCI.ui.devices.DevicesScreen
import com.hci.TP3_HCI.ui.lamp.LampScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME.route
    ) {
        composable(route = AppDestinations.HOME.route) {
            HomeScreen()
        }
        composable(route = AppDestinations.DEVICES.route) {
            DevicesScreen(
                onNavigateToLamp = {
                    navController.navigate(route = AppDestinations.LAMP.route)
                }
            )
        }
        composable(route = AppDestinations.LAMP.route) {
            LampScreen()
        }
    }
}

