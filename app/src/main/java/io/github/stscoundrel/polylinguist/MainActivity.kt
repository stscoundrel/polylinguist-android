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
import io.github.stscoundrel.polylinguist.ui.CurrentStatisticsScreen
import io.github.stscoundrel.polylinguist.ui.CurrentStatisticsViewModel
import io.github.stscoundrel.polylinguist.ui.theme.PolylinguistTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app: PolylinguistApplication = application as PolylinguistApplication
        val currentStatsUseCase = app.container.getCurrentStatisticsUseCase
        val latestStatisticsUseCase = app.container.getLatestStatisticsUseCase

        setContent {
            PolylinguistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrentStatisticsScreen(
                        CurrentStatisticsViewModel(
                            currentStatsUseCase,
                            latestStatisticsUseCase
                        )
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