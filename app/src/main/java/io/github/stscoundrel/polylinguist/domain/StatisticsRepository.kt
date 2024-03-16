package io.github.stscoundrel.polylinguist.domain

import java.time.LocalDate

interface StatisticsRepository {
    suspend fun getCurrent(): Statistics

    suspend fun getByDate(date: LocalDate): Statistics

    suspend fun save(statistic: Statistics)

}