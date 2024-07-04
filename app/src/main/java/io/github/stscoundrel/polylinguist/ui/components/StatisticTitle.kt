package io.github.stscoundrel.polylinguist.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.stscoundrel.polylinguist.domain.Statistic
import io.github.stscoundrel.polylinguist.ui.utils.formatPresentationPercentage


@Composable
fun StatisticTitle(
    presentationIndex: Int,
    statistic: Statistic,
    comparison: Statistic? = null
) {
    val roundedPercentage = formatPresentationPercentage(statistic.percentage)
    val difference = if (comparison != null) {
        statistic.percentage - comparison.percentage
    } else 0.0

    Row {
        Text(
            text = "$presentationIndex. ${statistic.language} - $roundedPercentage",
            modifier = Modifier.padding(bottom = 2.dp)
        )
        ComparisonPercentage(difference)
    }
}