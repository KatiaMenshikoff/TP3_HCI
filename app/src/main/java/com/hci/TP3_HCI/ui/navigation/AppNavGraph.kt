package com.hci.TP3_HCI.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hci.TP3_HCI.ui.views.DevicesScreen
import com.hci.TP3_HCI.ui.views.AutomationsScreen
import com.hci.TP3_HCI.ui.views.HomeScreen
import com.hci.TP3_HCI.ui.devices.NewDevicesScreen
import com.hci.TP3_HCI.ui.lamp.LampScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME.route
    ) {
        composable(AppDestinations.HOME.route) {
            HomeScreen()
        }
        composable(AppDestinations.DEVICES.route) {
            NewDevicesScreen()
        }
        composable(AppDestinations.LAMP.route) {
            LampScreen()
        }
    }
}