package io.github.stscoundrel.polylinguist.data.network

import io.github.stscoundrel.polylinguist.testdata.StatisticFactory
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NetworkStatisticsServiceTest {
    @Mock
    lateinit var polylinguistAPIService: PolylinguistHTTPService

    private val statisticsService by lazy { NetworkStatisticsService(polylinguistAPIService) }

    @Test
    fun getCurrentStatisticsTest() = runBlocking {
        val responseList = listOf(
            StatisticFactory.createNetworkStatistic(language = "Java", size = 2000),
            StatisticFactory.createNetworkStatistic(language = "Kotlin", size = 4000),
        )

        // Mock HTTP service response.
        `when`(polylinguistAPIService.getStatistics()).thenReturn(responseList)

        // Perform the fetch operation
        val result = statisticsService.getCurrentStatistics()

        // Verify that the result matches the expected data
        assertEquals(responseList, result)
    }
}