package com.hci.TP3_HCI.ui.theme

import androidx.compose.ui.graphics.Color
import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.colorResource(@ColorRes id: Int): Color {
    return Color(ContextCompat.getColor(this, id))
}

//
//val background = Color(0xF2F6FF)
//val device = Color(0x6839BB)
//val automation = Color(0xF99C2C)
//val button = Color(0x00C0CF)
//val navBar = Color(0xFFFFFF)
//val grey = Color(0xB4B3B3)
//val red = Color(0xFF0000)
//val pinkMenu = Color(0xF8F0FC)