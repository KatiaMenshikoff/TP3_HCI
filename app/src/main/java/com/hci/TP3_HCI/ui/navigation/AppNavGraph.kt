package com.hci.TP3_HCI.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hci.TP3_HCI.ui.ac.ACScreen
import com.hci.TP3_HCI.ui.views.HomeScreen
import com.hci.TP3_HCI.ui.devices.DevicesScreen
import com.hci.TP3_HCI.ui.lamp.LampScreen
import com.hci.TP3_HCI.ui.settings.SettingsScreen
import com.hci.TP3_HCI.ui.speaker.SpeakerScreen
import com.hci.TP3_HCI.ui.sprinkler.SprinklerScreen

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
                    deviceId ->
                    navController.navigate(route = "${AppDestinations.LAMP.route}/$deviceId")
                },
                onNavigateToAC = {
                    deviceId ->
                    navController.navigate(route = "${AppDestinations.AC.route}/$deviceId")
                },
                onNavigateToSpeaker = {
                    deviceId ->
                    navController.navigate(route = "${AppDestinations.SPEAKER.route}/$deviceId")
                },
                onNavigateToSprinkler = {
                    deviceId ->
                    navController.navigate(route = "${AppDestinations.SPRINKLER.route}/$deviceId")
                }
            )
        }
        composable(route = "${AppDestinations.LAMP.route}/{deviceId}",
            arguments = listOf(navArgument("deviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            LampScreen(deviceId = deviceId!!)
        }
        composable(route = "${AppDestinations.AC.route}/{deviceId}",
            arguments = listOf(navArgument("deviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            ACScreen(deviceId = deviceId!!)
        }
        composable(route = "${AppDestinations.SPEAKER.route}/{deviceId}",
            arguments = listOf(navArgument("deviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            SpeakerScreen(deviceId = deviceId!!)
        }
        composable(route = "${AppDestinations.SPRINKLER.route}/{deviceId}",
            arguments = listOf(navArgument("deviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            SprinklerScreen(deviceId = deviceId!!)
        }
        composable(route = AppDestinations.SETTINGS.route){
            SettingsScreen()
        }
    }
}

