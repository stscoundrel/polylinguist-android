package io.github.stscoundrel.polylinguist.data.network.inmemory

import io.github.stscoundrel.polylinguist.data.inmemory.InMemoryStatistic
import io.github.stscoundrel.polylinguist.data.inmemory.InMemoryStatisticsProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate


class InMemoryStatisticsProviderTest {
    private lateinit var provider: InMemoryStatisticsProvider

    @Before
    fun initRepository() {
        provider = InMemoryStatisticsProvider()
    }

    @Test
    fun getsInitialStatisticsTest() = runBlocking {
        val result = provider.getStatistics()

        assertEquals(LocalDate.of(2024, 3, 17), result.date)
        assertEquals(16, result.statistics.size)

        val expectedTopStat = InMemoryStatistic(
            language = "TypeScript",
            size = 1027363,
            percentage = 38.92583705498804,
            color = "#3178c6",
        )

        assertEquals(expectedTopStat, result.statistics.first())
    }
}