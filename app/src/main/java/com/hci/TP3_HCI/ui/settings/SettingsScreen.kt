package com.hci.TP3_HCI.ui.settings

import android.content.Context
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hci.TP3_HCI.R
import java.util.*

@Composable
fun SettingsScreen() {
    var selectedLanguage by remember { mutableStateOf("en") }
    val context = LocalContext.current

    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.views_settings_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // RadioButton for English
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                RadioButton(
                    selected = selectedLanguage == "en",
                    onClick = {
                        selectedLanguage = "en"
                        updateLanguage(context, "en")
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "English",
                    fontSize = 18.sp,
                    color = if (selectedLanguage == "en") Color.Blue else Color.Black
                )
            }

            // RadioButton for Spanish
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                RadioButton(
                    selected = selectedLanguage == "es",
                    onClick = {
                        selectedLanguage = "es"
                        updateLanguage(context, "es")
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Español",
                    fontSize = 18.sp,
                    color = if (selectedLanguage == "es") Color.Blue else Color.Black
                )
            }
        }
    }
}

private fun updateLanguage(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
    // Notify changes to UI
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
    (context as? ComponentActivity)?.recreate()
}

