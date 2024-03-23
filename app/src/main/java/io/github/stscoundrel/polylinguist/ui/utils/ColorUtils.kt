package io.github.stscoundrel.polylinguist.ui.utils

import androidx.compose.ui.graphics.Color

fun createColorFromHex(colorHex: String): Color {
    return Color(android.graphics.Color.parseColor(colorHex))
}