package io.github.stscoundrel.polylinguist.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.stscoundrel.polylinguist.ui.utils.formatPresentationPercentage
import io.github.stscoundrel.polylinguist.ui.utils.getPercentPrefix
import kotlin.math.abs

@Composable
fun ComparisonPercentage(
    difference: Double,
) {
    if (difference != 0.0) {
        val comparisonText = if (abs(difference) >= 0.01) {
            "(${getPercentPrefix(difference)}${formatPresentationPercentage(abs(difference))})"
        } else ""
        val comparisonColor = if (difference < 0) Color.Red else Color.Green

        Text(
            text = comparisonText,
            color = comparisonColor,
            modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp)
        )
    }
}