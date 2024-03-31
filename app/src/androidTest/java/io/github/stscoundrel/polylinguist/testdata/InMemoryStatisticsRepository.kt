package io.github.stscoundrel.polylinguist.testdata

import io.github.stscoundrel.polylinguist.domain.Statistic
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.StatisticsRepository
import kotlinx.coroutines.delay
import java.time.LocalDate

class InMemoryStatisticsRepository(
    private val latestStatistics: Statistics,
    private val currentStatistics: Statistics,
) : StatisticsRepository {
    val statistics: MutableMap<LocalDate, List<Statistic>> = mutableMapOf()

    override suspend fun getHistory(startDate: LocalDate, endDate: LocalDate): List<Statistics> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrent(): Statistics {
        delay(1500)
        return currentStatistics
    }

    override suspend fun getLatest(): Statistics {
        delay(1500)
        return latestStatistics
    }

    override suspend fun getByDate(date: LocalDate): Statistics {
        TODO("Not yet implemented")
    }

    override suspend fun save(statistic: Statistics) {
        statistics.put(statistic.date, statistic.statistics)
    }
}