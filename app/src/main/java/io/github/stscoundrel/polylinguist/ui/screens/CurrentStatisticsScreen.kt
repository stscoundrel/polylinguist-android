package io.github.stscoundrel.polylinguist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavHostController
import io.github.stscoundrel.polylinguist.ui.NavigationRoutes
import io.github.stscoundrel.polylinguist.ui.components.HeadingText
import io.github.stscoundrel.polylinguist.ui.components.LoadingSpinner
import io.github.stscoundrel.polylinguist.ui.components.NavigationButton
import io.github.stscoundrel.polylinguist.ui.components.Statistics
import io.github.stscoundrel.polylinguist.ui.viewmodels.CurrentStatisticsViewModel


@Composable
fun CurrentStatisticsScreen(
    navController: NavHostController,
    statsViewModel: CurrentStatisticsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val statistics by statsViewModel.statistics.collectAsState()
    val isLoading by statsViewModel.isLoading.collectAsState()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HeadingText("Current Statistics")

        if (statistics == null) {
            LoadingSpinner()
        }

        statistics?.let { stats ->
            Statistics(stats)
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

        NavigationButton(
            navController,
            "Statistics History",
            NavigationRoutes.StatisticsHistory.route
        )

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


