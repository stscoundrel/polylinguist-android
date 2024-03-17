package io.github.stscoundrel.polylinguist.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import java.time.LocalDate

@Dao
interface StatisticDao {
    @Query("SELECT * FROM statistics")
    fun getAll(): List<StatisticEntity>

    @Upsert
    fun upsert(statistic: StatisticEntity)

    @Upsert
    fun upsertAll(statistics: List<StatisticEntity>)

    @Delete
    fun delete(statistic: StatisticEntity)

    @Query("SELECT * FROM statistics WHERE date = :date")
    fun getByDate(date: LocalDate): List<StatisticEntity>

    @Query("SELECT * FROM statistics WHERE date = (SELECT MAX(date) FROM statistics)")
    fun getLatestStatistics(): List<StatisticEntity>

    @Query("SELECT MAX(date) FROM statistics")
    fun getLatestDate(): LocalDate
}