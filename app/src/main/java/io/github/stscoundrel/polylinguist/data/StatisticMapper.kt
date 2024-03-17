package io.github.stscoundrel.polylinguist.data

import io.github.stscoundrel.polylinguist.data.database.StatisticEntity
import io.github.stscoundrel.polylinguist.data.inmemory.InMemoryStatistic
import io.github.stscoundrel.polylinguist.data.network.NetworkStatistic
import io.github.stscoundrel.polylinguist.domain.Statistic
import java.time.LocalDate

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

fun createStatisticFromInMemoryStatistic(inMemoryStatistic: InMemoryStatistic): Statistic {
    return Statistic(
        language = inMemoryStatistic.language,
        size = inMemoryStatistic.size,
        percentage = inMemoryStatistic.percentage,
        color = inMemoryStatistic.color,
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
