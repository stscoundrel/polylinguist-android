package io.github.stscoundrel.polylinguist.ui.utils

import io.github.stscoundrel.polylinguist.domain.Statistics
import java.util.Locale

const val kilobyteInBytes = 1024L
const val megabyteInBytes = kilobyteInBytes * 1024

fun formatBytes(bytes: Int): String {
    return when {
        bytes >= megabyteInBytes -> String.format(
            Locale.US,
            "%.3f MB",
            bytes.toDouble() / megabyteInBytes
        )

        bytes >= kilobyteInBytes -> String.format(
            Locale.US,
            "%.2f KB",
            bytes.toDouble() / kilobyteInBytes
        )

        else -> "$bytes bytes"
    }
}

fun getStatisticsByteSizeDifference(
    statistics: Statistics,
    comparisons: Statistics? = null
): Double {
    if (comparisons == null) {
        return 0.0
    }
    val delta = statistics.size - comparisons.size
    return (delta.toDouble() / comparisons.size) * 100
}