package io.github.stscoundrel.polylinguist.domain

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