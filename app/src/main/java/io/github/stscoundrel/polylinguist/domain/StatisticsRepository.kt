package io.github.stscoundrel.polylinguist.domain

import java.time.LocalDate

interface StatisticsRepository {
    suspend fun getHistory(startDate: LocalDate, endDate: LocalDate): List<Statistics>
    suspend fun getCurrent(): Statistics

    suspend fun getLatest(): Statistics

    suspend fun getByDate(date: LocalDate): Statistics

    suspend fun save(statistic: Statistics)

}