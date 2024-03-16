package io.github.stscoundrel.polylinguist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import io.github.stscoundrel.polylinguist.ui.theme.CurrentStatisticsScreen
import io.github.stscoundrel.polylinguist.ui.theme.CurrentStatisticsViewModel
import io.github.stscoundrel.polylinguist.ui.theme.PolylinguistTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app: PolylinguistApplication = application as PolylinguistApplication
        val useCase = app.container.getCurrentStatisticsUseCase

        // TODO: Debug only.
        lifecycleScope.launch {
            val stats = useCase()
            Log.v("Lol", stats.date.toString())
            for (stat in stats.statistics) {
                Log.v("Lol", stat.toString())
            }
        }

        setContent {
            PolylinguistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrentStatisticsScreen(CurrentStatisticsViewModel(useCase))
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