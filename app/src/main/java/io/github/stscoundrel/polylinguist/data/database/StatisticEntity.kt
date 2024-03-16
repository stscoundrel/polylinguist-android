package io.github.stscoundrel.polylinguist.data.database

import androidx.room.Entity
import java.time.LocalDate

@Entity(tableName = "statistics", primaryKeys = ["date", "language"])
data class StatisticEntity(
    val language: String,
    val size: Int,
    val percentage: Double,
    val color: String,
    val date: LocalDate
)