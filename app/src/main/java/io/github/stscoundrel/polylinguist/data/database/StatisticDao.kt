package io.github.stscoundrel.polylinguist.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StatisticDao {
    @Query("SELECT * FROM statistics")
    fun getAll(): List<StatisticEntity>

    @Insert
    fun insert(statistic: StatisticEntity)

    @Delete
    fun delete(statistic: StatisticEntity)
}