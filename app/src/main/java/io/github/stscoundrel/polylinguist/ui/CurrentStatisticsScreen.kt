package io.github.stscoundrel.polylinguist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.format.DateTimeFormatter


@Composable
fun CurrentStatisticsScreen(
    statsViewModel: CurrentStatisticsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val statistics by statsViewModel.statistics.collectAsState()
    val isLoading by statsViewModel.isLoading.collectAsState()

    if (statistics == null) {
        Text(text = "Loading...")
    } else {

        Column(modifier = Modifier.fillMaxSize()) {

            statistics?.let { stats ->
                Text(text = "Date: ${stats.date.format(DateTimeFormatter.ISO_DATE)}")
                Spacer(modifier = Modifier.height(8.dp))

                stats.statistics.sortedByDescending { it.percentage }
                    .forEachIndexed { index, statistic ->
                        val presentationIndex = index + 1
                        val roundedPercentage = "%.2f".format(statistic.percentage)
                        Text(
                            text = "$presentationIndex. ${statistic.language} - ${roundedPercentage}%",
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
            }

            if (!statsViewModel.hasUpToDateStatistics()) {
                Row {
                    Button(onClick = { statsViewModel.getCurrentStatistics() }) {
                        Text(text = "Update")
                    }

                    if (isLoading) {
                        Text(text = "LOADING!")
                    }
                }
            }
        }
    }
}
