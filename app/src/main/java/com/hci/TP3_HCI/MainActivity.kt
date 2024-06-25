package com.hci.TP3_HCI

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("coolhome", name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        var builder = NotificationCompat.Builder(this, "coolhome")
            .setSmallIcon(R.drawable.icon_device)
            .setContentTitle("CoolHome app language changed")
            .setContentText("Language has been set to English")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val coroutineScope = rememberCoroutineScope()
            val configuration = LocalConfiguration.current
            val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

            // Check and request permission for notifications if necessary
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val permissionState = rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
                if (!permissionState.status.isGranted) {
                    LaunchedEffect(Unit) {
                        coroutineScope.launch {
                            permissionState.launchPermissionRequest()
                        }
                    }

                    Text(text = stringResource(R.string.permission_request_notification))
                }
            }

            if(!isLandscape) {
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
            } else {
                Row(modifier = Modifier.fillMaxSize()) {
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
                    Box(modifier = Modifier.weight(1f)) {
                        AppNavGraph(navController = navController)
                    }
                }

            }

        }
    }
}
