package io.github.stscoundrel.polylinguist.ui.utils

import java.util.Locale

const val kilobyteInBytes = 1024L
const val megabyteInBytes = kilobyteInBytes * 1024

fun formatBytes(bytes: Int, comparisonBytes: Int? = null): String {
    val comparisonText = if (comparisonBytes != null) {
        formatComparison(bytes, comparisonBytes)
    } else ""
    val result = when {
        bytes >= megabyteInBytes -> String.format(
            Locale.US,
            "%.3f MB ",
            bytes.toDouble() / megabyteInBytes
        ) + comparisonText

        bytes >= kilobyteInBytes -> String.format(
            Locale.US,
            "%.2f KB ",
            bytes.toDouble() / kilobyteInBytes
        ) + comparisonText

        else -> "$bytes bytes $comparisonText"
    }

    return result.trim()
}

