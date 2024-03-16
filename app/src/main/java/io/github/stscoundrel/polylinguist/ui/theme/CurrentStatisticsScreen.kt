package io.github.stscoundrel.polylinguist.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.stscoundrel.polylinguist.domain.Statistics
import java.time.format.DateTimeFormatter


@Composable
fun CurrentStatisticsScreen(
    statsViewModel: CurrentStatisticsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var currentStatistics by remember { mutableStateOf<Statistics?>(null) }

    statsViewModel.currentStatistics.value?.let { stats ->
        currentStatistics = stats
    }

    if (currentStatistics == null) {
        Text(text = "Loading...")
    } else {

        Column {
            currentStatistics?.let { stats ->
                // Render statistics data
                Text(text = "Date: ${stats.date.format(DateTimeFormatter.ISO_DATE)}")
                Spacer(modifier = Modifier.height(8.dp))

                stats.statistics.forEachIndexed { index, statistic ->
                    val presentationIndex = index + 1
                    val roundedPercentage = "%.2f".format(statistic.percentage)
                    Text(
                        text = "$presentationIndex. ${statistic.language} - ${roundedPercentage}%",
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }
        }
    }

}