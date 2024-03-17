package io.github.stscoundrel.polylinguist.data

import io.github.stscoundrel.polylinguist.data.database.StatisticDao
import io.github.stscoundrel.polylinguist.data.network.StatisticsService
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.StatisticsRepository
import java.time.LocalDate

class DefaultStatisticsRepository(
    private val networkStatisticsService: StatisticsService,
    private val statisticsDao: StatisticDao
) : StatisticsRepository {
    override suspend fun getCurrent(): Statistics {
        val networkStatistic = networkStatisticsService.getCurrentStatistics()

        val statistics = networkStatistic.map { createStatisticFromNetWorkStatistic(it) }

        return Statistics(
            statistics = statistics,
            date = LocalDate.now()
        )
    }

    override suspend fun getLatest(): Statistics? {
        val statistics = statisticsDao.getLatestStatistics()

        if (statistics.isEmpty()) {
            return null
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
}