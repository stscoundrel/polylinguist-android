package io.github.stscoundrel.polylinguist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.stscoundrel.polylinguist.ui.components.HorizontalBar
import io.github.stscoundrel.polylinguist.ui.components.LoadingSpinner
import io.github.stscoundrel.polylinguist.ui.utils.formatPresentationDate
import io.github.stscoundrel.polylinguist.ui.viewmodels.CurrentStatisticsViewModel


@Composable
fun CurrentStatisticsScreen(
    statsViewModel: CurrentStatisticsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val statistics by statsViewModel.statistics.collectAsState()
    val isLoading by statsViewModel.isLoading.collectAsState()



    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (statistics == null) {
            LoadingSpinner()
        }

        statistics?.let { stats ->
            Text(text = "Date: ${formatPresentationDate(stats.date)}")
            Spacer(modifier = Modifier.height(8.dp))

            val largestPercentage = stats.statistics.maxByOrNull { it.percentage }!!
                .percentage

            stats.statistics.sortedByDescending { it.percentage }
                .forEachIndexed { index, statistic ->
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

        if (!statsViewModel.hasUpToDateStatistics()) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Column {

                    Button(
                        onClick = { statsViewModel.getCurrentStatistics() },
                        enabled = !isLoading
                    ) {
                        Text(text = "Update")
                    }
                    Spacer(modifier = modifier.padding(16.dp))
                }
            }

        }

        if (isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {

                LoadingSpinner()
            }
        }
    }


}


