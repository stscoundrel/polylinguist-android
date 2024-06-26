package io.github.stscoundrel.polylinguist.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import io.github.stscoundrel.polylinguist.ui.components.StatisticsPager
import io.github.stscoundrel.polylinguist.ui.viewmodels.StatisticsHistoryViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatisticsHistoryScreen(
    navController: NavHostController,
    historyViewModel: StatisticsHistoryViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val history by historyViewModel.history.collectAsState()
    val isLoading by historyViewModel.isLoading.collectAsState()

    val pagerState = rememberPagerState(pageCount = {
        history.size
    })

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        HeadingText("Statistics History")

        if (isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {

                LoadingSpinner()
            }
        }

        StatisticsPager(pagerState, history)
        Spacer(modifier = modifier.padding(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { historyViewModel.updateHistory() },
                enabled = !isLoading
            ) {
                Text(text = "Update")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            NavigationButton(
                navController,
                "Current statistics",
                NavigationRoutes.CurrentStatistics.route
            )
        }
    }


}


