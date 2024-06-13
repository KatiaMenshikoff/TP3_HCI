package com.hci.TP3_HCI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.hci.TP3_HCI.ui.Components.BottomNavigationBar
import com.hci.TP3_HCI.ui.Components.SprinklersCard
import com.hci.TP3_HCI.ui.theme.CoolHomeTheme
import com.hci.TP3_HCI.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoolHomeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(R.color.background)
                ) {
                    Scaffold(
                        bottomBar = { BottomNavigationBar() }
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SprinklersCard(
                                title = "Hola",
                                time = "Today",
                                actions = "2 actions",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)  // Adjust the height as necessary
                            )
                        }
                    }
                }
            }
        }
    }
}
