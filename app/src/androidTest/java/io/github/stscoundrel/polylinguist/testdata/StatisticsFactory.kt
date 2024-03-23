package io.github.stscoundrel.polylinguist.testdata

import io.github.stscoundrel.polylinguist.data.database.StatisticEntity
import io.github.stscoundrel.polylinguist.data.network.NetworkStatistic
import io.github.stscoundrel.polylinguist.domain.Statistic
import java.time.LocalDate

class StatisticsFactory {
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