package com.hci.TP3_HCI.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.hci.TP3_HCI.R

enum class AppDestinations(@StringRes val title: Int, val route: String) {
    HOME(R.string.views_home_name, "home_screen"),
    DEVICES(R.string.views_devices_name, "devices_screen"),
    SETTINGS(R.string.views_settings_name, "settings_screen"),
    LAMP(R.string.views_lamp_name, "lamp_screen"),
    AC(R.string.views_ac_name, "ac_screen"),
    SPEAKER(R.string.views_speaker_name, "speaker_screen"),
    SPRINKLER(R.string.views_sprinkler_name, "sprinkler_screen")
}
