package com.hci.TP3_HCI.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hci.TP3_HCI.ui.views.DevicesScreen
import com.hci.TP3_HCI.ui.views.AutomationsScreen
import com.hci.TP3_HCI.ui.views.HomeView

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME.route
    ) {
        composable(AppDestinations.HOME.route) {
            HomeView()
        }
//        composable(AppDestinations.DEVICES.route) {
//            DevicesScreen()
//        }
//        composable(AppDestinations.AUTOMATIONS.route) {
//            AutomationsScreen()
//        }
    }
}