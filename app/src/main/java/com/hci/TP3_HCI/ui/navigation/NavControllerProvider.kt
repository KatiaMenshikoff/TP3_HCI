package com.hci.TP3_HCI.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

// Se puede importar esta variable desde cualquier composable y acceder
// al controlador de navegación desde cualquier lugar del proyecto.
val LocalNavHostController = staticCompositionLocalOf<NavHostController> { error("NavHostController not provided") }

// Se inicializa la variable local static de LocalNavHostController. Ademas,
// todos los composables envueltos dentro del llamado de la funcion recibiran
// el controlador de navegación, por contexto.
@Composable
fun ProvideNavHostController(navHostController: NavHostController, content: @Composable () -> Unit) {
    androidx.compose.runtime.CompositionLocalProvider(LocalNavHostController provides navHostController, content = content)
}
