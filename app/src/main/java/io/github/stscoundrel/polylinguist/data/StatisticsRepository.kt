package io.github.stscoundrel.polylinguist.data

import io.github.stscoundrel.polylinguist.data.database.StatisticDao
import io.github.stscoundrel.polylinguist.data.network.StatisticsService
import java.time.LocalDate

class StatisticsRepository(
    private val networkStatisticsService: StatisticsService,
    private val statisticsDao: StatisticDao
) {
    suspend fun getCurrent(): Statistics {
        val networkStatistic = networkStatisticsService.getCurrentStatistics()

        val statistics = networkStatistic.map { createStatisticFromNetWorkStatistic(it) }

        return Statistics(
            statistics = statistics,
            date = LocalDate.now()
        )
    }

    suspend fun getByDate(date: LocalDate): Statistics {
        val statistics = statisticsDao.getByDate(date)

        return Statistics(
            date = date,
            statistics = statistics.map { createStatisticFromEntity(it) }
        )
    }

    suspend fun save(statistic: Statistics) {
        statisticsDao.upsertAll(
            statistic.statistics
                .map {
                    createEntityFromStatistic(it, statistic.date)
                }
        )
    }
}