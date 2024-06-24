package io.github.stscoundrel.polylinguist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import io.github.stscoundrel.polylinguist.ui.Navigation
import io.github.stscoundrel.polylinguist.ui.theme.PolylinguistTheme
import io.github.stscoundrel.polylinguist.ui.viewmodels.CurrentStatisticsViewModel
import io.github.stscoundrel.polylinguist.ui.viewmodels.StatisticsHistoryViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app: PolylinguistApplication = application as PolylinguistApplication
        val currentStatsUseCase = app.container.getCurrentStatisticsUseCase
        val latestStatisticsUseCase = app.container.getLatestStatisticsUseCase
        val statisticsHistoryUseCase = app.container.getStatisticsHistoryUseCase

        val currentStatsViewModel = CurrentStatisticsViewModel(
            currentStatsUseCase,
            latestStatisticsUseCase
        )
        val historyViewModel = StatisticsHistoryViewModel(
            statisticsHistoryUseCase
        )

        setContent {
            PolylinguistTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Navigation(
                        navController = navController,
                        currentStatisticsViewModel = currentStatsViewModel,
                        statisticsHistoryViewModel = historyViewModel
                    )
                }
            }
        }


    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PolylinguistTheme {
        Greeting("Android")
    }
}