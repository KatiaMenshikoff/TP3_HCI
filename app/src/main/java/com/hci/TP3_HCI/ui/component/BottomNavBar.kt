package com.hci.TP3_HCI.ui.component

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.hci.TP3_HCI.R

@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Devices", "Automation", "Settings")

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    val icon = when (item) {
                        "Home" -> painterResource(id = R.drawable.icon_home)
                        "Devices" -> painterResource(id = R.drawable.icon_device)
                        "Automation" -> painterResource(id = R.drawable.icon_automation)
                        "Settings" -> painterResource(id = R.drawable.icon_settings)
                        else -> painterResource(id = R.drawable.icon_home)
                    }
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = if (selectedItem == index) colorResource(R.color.grey) else colorResource(R.color.grey)
                    )
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.background(if (selectedItem == index) colorResource(R.color.pinkMenu) else Color.Transparent)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}
