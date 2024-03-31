package io.github.stscoundrel.polylinguist.data

import io.github.stscoundrel.polylinguist.data.database.StatisticDao
import io.github.stscoundrel.polylinguist.data.inmemory.InMemoryStatisticsProvider
import io.github.stscoundrel.polylinguist.data.network.StatisticsService
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.StatisticsRepository
import java.time.LocalDate

class DefaultStatisticsRepository(
    private val networkStatisticsService: StatisticsService,
    private val statisticsDao: StatisticDao,
    private val inMemoryStatisticsProvider: InMemoryStatisticsProvider
) : StatisticsRepository {
    override suspend fun getHistory(startDate: LocalDate, endDate: LocalDate): List<Statistics> {
        val initialStatistics = getInitialStats()
        val dbStatisticsLists =
            createStatisticsListFromStatisticEntries(statisticsDao.getByDates(startDate, endDate))

        if (initialStatistics.date in startDate..endDate) {
            return dbStatisticsLists + initialStatistics
        }

        return dbStatisticsLists
    }

    override suspend fun getCurrent(): Statistics {
        val networkStatistic = networkStatisticsService.getCurrentStatistics()

        val statistics = networkStatistic.map { createStatisticFromNetWorkStatistic(it) }

        return Statistics(
            statistics = statistics,
            date = LocalDate.now()
        )
    }

    override suspend fun getLatest(): Statistics {
        val statistics = statisticsDao.getLatestStatistics()

        // If DB result is not available, default to initial in-memory stats.
        if (statistics.isEmpty()) {
            return getInitialStats()
        }

        return Statistics(
            date = statistics.first().date,
            statistics = statistics.map { createStatisticFromEntity(it) }
        )
    }

    override suspend fun getByDate(date: LocalDate): Statistics {
        val statistics = statisticsDao.getByDate(date)

        return Statistics(
            date = date,
            statistics = statistics.map { createStatisticFromEntity(it) }
        )
    }

    override suspend fun save(statistic: Statistics) {
        statisticsDao.upsertAll(
            statistic.statistics
                .map {
                    createEntityFromStatistic(it, statistic.date)
                }
        )
    }

    private fun getInitialStats(): Statistics {
        val initialStats = inMemoryStatisticsProvider.getStatistics()

        return Statistics(
            date = initialStats.date,
            statistics = initialStats.statistics.map { createStatisticFromInMemoryStatistic(it) },
        )
    }
}