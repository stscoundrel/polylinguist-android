package io.github.stscoundrel.polylinguist.testdata

import io.github.stscoundrel.polylinguist.domain.Statistic
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.StatisticsRepository
import java.time.LocalDate

class InMemoryStatisticsRepository(
    private val latestStatistics: Statistics,
    private val currentStatistics: Statistics,
) : StatisticsRepository {
    val statistics: MutableMap<LocalDate, List<Statistic>> = mutableMapOf()
    override suspend fun getHistory(startDate: LocalDate, endDate: LocalDate): List<Statistics> {
        return statistics
            .filterKeys { it in startDate..endDate }
            .map { (date, stats) -> Statistics(date = date, statistics = stats) }
    }

    override suspend fun getCurrent(): Statistics {
        return currentStatistics
    }

    override suspend fun getLatest(): Statistics {
        return latestStatistics
    }

    override suspend fun getByDate(date: LocalDate): Statistics {
        TODO("Not yet implemented")
    }

    override suspend fun save(statistic: Statistics) {
        statistics.put(statistic.date, statistic.statistics)
    }

    fun setTestStats(stats: List<Statistics>) {
        stats.forEach { statistics[it.date] = it.statistics }
    }
}