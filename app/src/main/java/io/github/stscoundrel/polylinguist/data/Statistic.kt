package io.github.stscoundrel.polylinguist.data

import io.github.stscoundrel.polylinguist.data.database.StatisticEntity
import io.github.stscoundrel.polylinguist.data.network.NetworkStatistic
import java.time.LocalDate

data class Statistic(
    val language: String,
    val percentage: Double,
    val color: String,
)

data class Statistics(
    val statistics: List<Statistic>,
    val date: LocalDate
)

fun createStatisticFromNetWorkStatistic(networkStatistic: NetworkStatistic): Statistic {
    return Statistic(
        language = networkStatistic.language,
        percentage = networkStatistic.percentage,
        color = networkStatistic.color,
    )
}

fun createEntityFromStatistic(statistic: Statistic, date: LocalDate): StatisticEntity {
    return StatisticEntity(
        language = statistic.language,
        percentage = statistic.percentage,
        color = statistic.color,
        date = date,
    )
}