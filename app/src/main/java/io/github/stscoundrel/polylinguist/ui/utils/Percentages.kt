package io.github.stscoundrel.polylinguist.ui.utils

import java.util.Locale
import kotlin.math.abs

fun formatPresentationPercentage(percent: Double): String {
    return "${"%.2f".format(Locale.US, percent)}%"
}

fun getPercentPrefix(percent: Double): String {
    if (percent < 0) {
        return "-"
    }

    return "+"
}

fun formatComparison(value: Int, comparisonValue: Int): String {
    val delta = value - comparisonValue
    val difference = (delta.toDouble() / comparisonValue) * 100

    return if (abs(difference) >= 0.01) {
        "(${getPercentPrefix(difference)}${formatPresentationPercentage(abs(difference))})"
    } else ""
}