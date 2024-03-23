package io.github.stscoundrel.polylinguist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.stscoundrel.polylinguist.ui.utils.createColorFromHex

@Composable
fun HorizontalBar(
    percentage: Double,
    largestPercentage: Double,
    colorHex: String,
    modifier: Modifier = Modifier
) {
    val clampedPercentage = (percentage / largestPercentage).coerceIn(0.0, 1.0)

    Box(
        modifier = modifier
            .height(8.dp)
            .padding(5.dp, 0.dp)
            .fillMaxWidth(clampedPercentage.toFloat())
            .background(createColorFromHex(colorHex))
    )
    Spacer(modifier = modifier.height(5.dp))
}