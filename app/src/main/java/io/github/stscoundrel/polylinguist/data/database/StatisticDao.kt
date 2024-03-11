package io.github.stscoundrel.polylinguist.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.time.LocalDate

@Dao
interface StatisticDao {
    @Query("SELECT * FROM statistics")
    fun getAll(): List<StatisticEntity>

    @Insert
    fun insert(statistic: StatisticEntity)

    @Insert
    fun insertAll(statistics: List<StatisticEntity>)

    @Delete
    fun delete(statistic: StatisticEntity)

    @Query("SELECT * FROM statistics WHERE date = :date")
    fun getByDate(date: LocalDate): List<StatisticEntity>
}