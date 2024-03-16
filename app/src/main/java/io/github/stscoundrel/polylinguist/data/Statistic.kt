package io.github.stscoundrel.polylinguist.data

import io.github.stscoundrel.polylinguist.data.database.StatisticEntity
import io.github.stscoundrel.polylinguist.data.network.NetworkStatistic
import java.time.LocalDate

data class Statistic(
    val language: String,
    val size: Int,
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
        size = networkStatistic.size,
        percentage = networkStatistic.percentage,
        color = networkStatistic.color,
    )
}

fun createStatisticFromEntity(entity: StatisticEntity): Statistic {
    return Statistic(
        language = entity.language,
        size = entity.size,
        percentage = entity.percentage,
        color = entity.color,
    )
}

fun createEntityFromStatistic(statistic: Statistic, date: LocalDate): StatisticEntity {
    return StatisticEntity(
        language = statistic.language,
        size = statistic.size,
        percentage = statistic.percentage,
        color = statistic.color,
        date = date,
    )
}