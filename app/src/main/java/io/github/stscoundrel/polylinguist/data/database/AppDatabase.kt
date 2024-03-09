package io.github.stscoundrel.polylinguist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.stscoundrel.polylinguist.data.database.util.LocalDateConverter

@Database(entities = [StatisticEntity::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun statisticsDao(): StatisticDao
}