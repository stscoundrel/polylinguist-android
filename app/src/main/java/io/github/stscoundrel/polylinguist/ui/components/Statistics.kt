package io.github.stscoundrel.polylinguist.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.ui.utils.formatBytes
import io.github.stscoundrel.polylinguist.ui.utils.formatPresentationDate
import io.github.stscoundrel.polylinguist.ui.utils.getStatisticsByteSizeDifference

@Composable
fun Statistics(statistics: Statistics, comparisons: Statistics? = null) {
    val sortedStats = statistics.statistics.sortedByDescending { it.percentage }
    val largestPercentage = sortedStats.first().percentage
    val difference = getStatisticsByteSizeDifference(statistics, comparisons)

    Text(
        text = "Statistics for ${formatPresentationDate(statistics.date)}."
    )
    Row {
        Text(
            text = "Total of ${
                formatBytes(
                    statistics.size
                )
            }"

        )
        ComparisonPercentage(difference = difference)
    }
    Spacer(modifier = Modifier.height(8.dp))

    sortedStats.forEachIndexed { index, statistic ->
        val presentationIndex = index + 1
        val comparison =
            comparisons?.statistics?.firstOrNull { it.language == statistic.language }
        StatisticTitle(presentationIndex, statistic, comparison)
        StatisticBar(
            statistic = statistic,
            largestPercentage = largestPercentage,
        )
    }
}

