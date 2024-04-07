package io.github.stscoundrel.polylinguist.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.stscoundrel.polylinguist.ui.components.LoadingSpinner
import io.github.stscoundrel.polylinguist.ui.components.StatisticsPager
import io.github.stscoundrel.polylinguist.ui.viewmodels.StatisticsHistoryViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatisticsHistoryScreen(
    historyViewModel: StatisticsHistoryViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val history by historyViewModel.history.collectAsState()
    val isLoading by historyViewModel.isLoading.collectAsState()

    val pagerState = rememberPagerState(pageCount = {
        history.size
    })

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
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
    }


}


