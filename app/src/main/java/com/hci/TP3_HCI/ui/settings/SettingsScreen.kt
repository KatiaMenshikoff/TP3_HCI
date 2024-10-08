package com.example.mobileapp.ui.views

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hci.TP3_HCI.R
import java.util.*


@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onLanguageChange: (String) -> Unit
) {
    val context = LocalContext.current
    val currentLanguage = context.resources.configuration.locales[0].language
    var selectedLanguage by remember { mutableStateOf(if (currentLanguage == "es") "Español" else "English") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(colorResource(R.color.background))
            .fillMaxSize()
            .padding(16.dp)
    ) {


        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.pinkButton)
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .shadow(10.dp, RoundedCornerShape(16.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = stringResource(id = R.string.views_settings_name), fontSize = 20.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(16.dp))

                Divider()

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = stringResource(id = R.string.select_language), fontSize = 16.sp, color = Color.Black)

                    val languages = listOf(
                        stringResource(id = R.string.language_english),
                        stringResource(id = R.string.language_spanish)
                    )

                    languages.forEach { language ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    selectedLanguage = language
                                    onLanguageChange(language)
                                    updateLanguage(
                                        context,
                                        if (language == "Español") "es" else "en"
                                    )
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedLanguage == language,
                                onClick = {
                                    selectedLanguage = language
                                    onLanguageChange(language)
                                    updateLanguage(context, if (language == "Español") "es" else "en")
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = colorResource(id = R.color.device),
                                    unselectedColor = Color.Gray
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = language, fontSize = 16.sp, color = Color.Black)
                        }
                    }
                }
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

    showLanguageChangedNotification(context)
    // Notify the change to UI
    (context as? ComponentActivity)?.recreate()
}

private fun showLanguageChangedNotification(context: Context) {
    val channelId = "coolhome"
    val notificationId = 1
    val notificationTitle = context.getString(R.string.language_changed)
    val notificationText = context.getString(R.string.language_changed_description)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.icon_device)
        .setContentTitle(notificationTitle)
        .setContentText(notificationText)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Consider requesting the permission
            return
        }
        notify(notificationId, builder.build())
    }
}
