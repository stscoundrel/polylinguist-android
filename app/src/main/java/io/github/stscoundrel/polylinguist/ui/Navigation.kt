package io.github.stscoundrel.polylinguist.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.stscoundrel.polylinguist.ui.screens.CurrentStatisticsScreen
import io.github.stscoundrel.polylinguist.ui.screens.StatisticsHistoryScreen
import io.github.stscoundrel.polylinguist.ui.viewmodels.CurrentStatisticsViewModel
import io.github.stscoundrel.polylinguist.ui.viewmodels.StatisticsHistoryViewModel


enum class NavigationRoutes(val route: String) {
    CurrentStatistics("current_statistics"),
    StatisticsHistory("statistics_history")
}

@Composable
fun Navigation(
    navController: NavHostController,
    currentStatisticsViewModel: CurrentStatisticsViewModel,
    statisticsHistoryViewModel: StatisticsHistoryViewModel
) {


    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.CurrentStatistics.route,
    ) {
        composable(NavigationRoutes.CurrentStatistics.route) {
            CurrentStatisticsScreen(navController, currentStatisticsViewModel)
        }
        composable(NavigationRoutes.StatisticsHistory.route) {
            StatisticsHistoryScreen(navController, statisticsHistoryViewModel)
        }
    }
}