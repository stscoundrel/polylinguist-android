package io.github.stscoundrel.polylinguist.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.ui.utils.formatPresentationDate

@Composable
fun Statistics(statistics: Statistics) {
    val sortedStats = statistics.statistics.sortedByDescending { it.percentage }

    Text(text = "Statistics for ${formatPresentationDate(statistics.date)}")
    Spacer(modifier = Modifier.height(8.dp))

    val largestPercentage = statistics.statistics.maxByOrNull { it.percentage }!!
        .percentage

    sortedStats.forEachIndexed { index, statistic ->
        val presentationIndex = index + 1
        val roundedPercentage = "%.2f".format(statistic.percentage)
        Text(
            text = "$presentationIndex. ${statistic.language} - ${roundedPercentage}%",
            modifier = Modifier.padding(bottom = 2.dp)
        )
        HorizontalBar(
            percentage = statistic.percentage,
            largestPercentage = largestPercentage,
            colorHex = statistic.color
        )
    }
}