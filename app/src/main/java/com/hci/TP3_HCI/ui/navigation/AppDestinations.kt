package com.hci.TP3_HCI.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.hci.TP3_HCI.R

enum class AppDestinations(@StringRes val title: Int, val route: String) {
    HOME(R.string.views_home_name, "home_screen"),
    DEVICES(R.string.views_devices_name, "devices_screen"),
    LAMP(R.string.views_lamp_name, "lamp_screen"),
}
