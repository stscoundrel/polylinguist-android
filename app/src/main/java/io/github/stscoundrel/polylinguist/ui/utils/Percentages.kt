package io.github.stscoundrel.polylinguist.ui.utils

import java.util.Locale

fun formatPresentationPercentage(percent: Double): String {
    return "${"%.2f".format(Locale.US, percent)}%"
}

fun getPercentPrefix(percent: Double): String {
    if (percent < 0) {
        return "-"
    }

    return "+"
}