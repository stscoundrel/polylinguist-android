package io.github.stscoundrel.polylinguist.domain

import io.github.stscoundrel.polylinguist.testdata.StatisticFactory
import io.github.stscoundrel.polylinguist.testdata.StatisticsFactory
import junit.framework.TestCase.assertEquals
import org.junit.Test

class StatisticTest {
    @Test
    fun getsStatisticsSize() {
        val emptyStatistics = StatisticsFactory.createStatistics()
        val statistics = StatisticsFactory.createStatistics(
            statistics = listOf(
                StatisticFactory.createStatistic(
                    size = 100,
                ),
                StatisticFactory.createStatistic(
                    size = 200,
                ),
                StatisticFactory.createStatistic(
                    size = 300,
                ),
                StatisticFactory.createStatistic(
                    size = 400,
                ),
                StatisticFactory.createStatistic(
                    size = 500,
                )
            )
        )

        assertEquals(emptyStatistics.size, 0)
        assertEquals(statistics.size, 1500)
    }
}