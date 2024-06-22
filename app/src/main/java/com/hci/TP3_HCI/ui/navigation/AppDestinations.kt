package com.hci.TP3_HCI.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.hci.TP3_HCI.R

enum class AppDestinations(@StringRes val title: Int, val icon: ImageVector, val route: String) {
    HOME(R.string.views_home_name, Icons.Filled.Home, "home_screen"),
    DEVICES(R.string.views_devices_name, Icons.Filled.Build, "devices_screen"),
    LAMP(R.string.views_lamp_name, Icons.Filled.Home, "lamp_screen"),
}
