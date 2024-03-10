package io.github.stscoundrel.polylinguist.data.network

interface StatisticsService {
    suspend fun getCurrentStatistics(): List<NetworkStatistic>
}

class NetworkStatisticsService(private val polylinguistApiService: PolylinguistHTTPService) :
    StatisticsService {
    override suspend fun getCurrentStatistics(): List<NetworkStatistic> =
        polylinguistApiService.getStatistics()
}