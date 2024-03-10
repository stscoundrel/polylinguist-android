package io.github.stscoundrel.polylinguist.data.network

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
            NetworkStatistic(
                language="Java", percentage=2.083404634958113, color="#b07219"
            ),
            NetworkStatistic(
                language="Kotlin", percentage=2.755940787285302, color="#A97BFF"
            ),
        )

        // Mock HTTP service response.
        `when`(polylinguistAPIService.getStatistics()).thenReturn(responseList)

        // Perform the fetch operation
        val result = statisticsService.getCurrentStatistics()

        // Verify that the result matches the expected data
        assertEquals(responseList, result)
    }
}