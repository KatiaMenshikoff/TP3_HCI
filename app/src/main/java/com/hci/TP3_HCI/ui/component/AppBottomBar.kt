package com.hci.TP3_HCI.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.hci.TP3_HCI.R
import com.hci.TP3_HCI.ui.navigation.AppDestinations

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigateToRoute: (String) -> Unit
) {
    val icons = mapOf(
        AppDestinations.DEVICES to ImageVector.vectorResource(id = R.drawable.icon_device),
        AppDestinations.SETTINGS to ImageVector.vectorResource(id = R.drawable.icon_settings),
    )

    val items = listOf(
        AppDestinations.DEVICES,
        AppDestinations.SETTINGS,
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = icons[item]!!, contentDescription = stringResource(item.title)) },
                label = { Text(text = stringResource(item.title)) },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = { onNavigateToRoute(item.route) }
            )
        }
    }
}