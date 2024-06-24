package com.hci.TP3_HCI

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.hci.TP3_HCI.ui.component.AppBottomBar
import com.hci.TP3_HCI.ui.navigation.AppNavGraph
import kotlinx.coroutines.launch
import java.util.jar.Manifest

@OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val coroutineScope = rememberCoroutineScope()

            // Check and request permission for notifications if necessary
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val permissionState = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
                if (!permissionState.status.isGranted) {
                    LaunchedEffect(Unit) {
                        coroutineScope.launch {
                            permissionState.launchPermissionRequest()
                        }
                    }

                    Text(text = "Esta aplicación necesita usar las notificaciones, por favor acepta el permiso para poder usarlas")
                }
            }

            Scaffold(
                bottomBar = {
                    AppBottomBar(
                        currentRoute = currentRoute
                    ) { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }
            ) {
                AppNavGraph(navController = navController)
            }
        }
    }
}
