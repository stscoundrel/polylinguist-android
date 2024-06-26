package io.github.stscoundrel.polylinguist.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.stscoundrel.polylinguist.domain.Statistic
import io.github.stscoundrel.polylinguist.ui.utils.formatPresentationPercentage
import io.github.stscoundrel.polylinguist.ui.utils.getPercentPrefix
import kotlin.math.abs

private fun getComparisonTextAndColor(
    statistic: Statistic,
    comparison: Statistic? = null
): Pair<String, Color> {
    if (comparison == null) {
        return Pair("", Color.DarkGray)
    }

    val difference = statistic.percentage - comparison.percentage
    val comparisonText = if (abs(difference) >= 0.01) {
        "(${getPercentPrefix(difference)}${formatPresentationPercentage(abs(difference))})"
    } else ""
    val comparisonColor = if (difference < 0) Color.Red else Color.Green

    return Pair(comparisonText, comparisonColor)
}

@Composable
fun StatisticTitle(
    presentationIndex: Int,
    statistic: Statistic,
    comparison: Statistic? = null
) {
    val roundedPercentage = formatPresentationPercentage(statistic.percentage)
    val (comparisonText, comparisonColor) = getComparisonTextAndColor(statistic, comparison)

    Row {
        Text(
            text = "$presentationIndex. ${statistic.language} - $roundedPercentage",
            modifier = Modifier.padding(bottom = 2.dp)
        )
        if (comparison != null) {
            Text(
                text = comparisonText,
                color = comparisonColor
            )
        }
    }
}