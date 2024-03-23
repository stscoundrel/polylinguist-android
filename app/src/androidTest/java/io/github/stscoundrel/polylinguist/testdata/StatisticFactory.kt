package io.github.stscoundrel.polylinguist.testdata

import io.github.stscoundrel.polylinguist.data.database.StatisticEntity
import io.github.stscoundrel.polylinguist.data.network.NetworkStatistic
import io.github.stscoundrel.polylinguist.domain.Statistic
import io.github.stscoundrel.polylinguist.domain.Statistics
import java.time.LocalDate


class StatisticsFactory {
    companion object {
        fun createStatistics(
            date: LocalDate = LocalDate.now(),
            statistics: List<Statistic> = listOf()
        ): Statistics {
            return Statistics(date = date, statistics = statistics)
        }
    }
}

class StatisticFactory {
    companion object {
        fun createNetworkStatistic(
            language: String = "Java",
            percentage: Double = 2.0,
            color: String = "#b07219",
            size: Int = 5000,
        ): NetworkStatistic {
            return NetworkStatistic(
                language = language,
                percentage = percentage,
                color = color,
                size = size,
            )
        }

        fun createStatistic(
            language: String = "Java",
            percentage: Double = 2.0,
            color: String = "#b07219",
            size: Int = 5000,
        ): Statistic {
            return Statistic(
                language = language,
                percentage = percentage,
                color = color,
                size = size,
            )
        }

        fun createStatisticEntity(
            language: String = "Java",
            percentage: Double = 2.0,
            color: String = "#b07219",
            size: Int = 5000,
            date: LocalDate = LocalDate.now()
        ): StatisticEntity {
            return StatisticEntity(
                language = language,
                percentage = percentage,
                color = color,
                size = size,
                date = date,
            )
        }
    }
}