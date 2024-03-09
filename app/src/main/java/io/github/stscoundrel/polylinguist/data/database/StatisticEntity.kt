package io.github.stscoundrel.polylinguist.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "statistics")
data class StatisticEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val language: String,
    val percentage: Double,
    val color: String,
    val date: LocalDate
)