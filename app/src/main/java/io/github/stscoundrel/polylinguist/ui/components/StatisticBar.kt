package io.github.stscoundrel.polylinguist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.github.stscoundrel.polylinguist.domain.Statistic
import io.github.stscoundrel.polylinguist.ui.utils.createColorFromHex

@Composable
fun StatisticBar(
    statistic: Statistic,
    largestPercentage: Double,
    modifier: Modifier = Modifier
) {
    val clampedPercentage = (statistic.percentage / largestPercentage).coerceIn(0.0, 1.0)

    Box(
        modifier = modifier
            .height(10.dp)
            .padding(10.dp, 0.dp)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .fillMaxWidth(clampedPercentage.toFloat())
            .background(createColorFromHex(statistic.color))
    )
    Spacer(modifier = modifier.height(8.dp))
}