package io.github.stscoundrel.polylinguist.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.ui.utils.formatBytes
import io.github.stscoundrel.polylinguist.ui.utils.formatPresentationDate

@Composable
fun Statistics(statistics: Statistics, comparisons: Statistics? = null) {
    val sortedStats = statistics.statistics.sortedByDescending { it.percentage }
    val largestPercentage = sortedStats.first().percentage

    Text(
        text = "Statistics for ${formatPresentationDate(statistics.date)}. Total of ${
            formatBytes(
                statistics.size
            )
        }"
    )
    Spacer(modifier = Modifier.height(8.dp))

    sortedStats.forEachIndexed { index, statistic ->
        val presentationIndex = index + 1
        val comparison =
            comparisons?.statistics?.first { it.language == statistic.language }
        StatisticTitle(presentationIndex, statistic, comparison)
        StatisticBar(
            statistic = statistic,
            largestPercentage = largestPercentage,
        )
    }
}

