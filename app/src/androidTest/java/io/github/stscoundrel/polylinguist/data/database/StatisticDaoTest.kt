package io.github.stscoundrel.polylinguist.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class StatisticDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var statisticsDao: StatisticDao

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        statisticsDao = database.statisticsDao()
    }

    @Test
    fun insertAndGetStatistics() = runTest {
        // Should be empty database at the start.
        val initialStatistics = statisticsDao.getAll()
        assertEquals(0, initialStatistics.size)


        val statistic1 = StatisticEntity(
            language = "Kotlin",
            percentage = 63.3,
            color = "FFFFF",
            date = LocalDate.of(2024, 1, 1)

        )

        val statistic2 = StatisticEntity(
            language = "Java",
            percentage = 36.7,
            color = "F4F4F4",
            date = LocalDate.of(2024, 1, 1)

        )

        statisticsDao.insert(statistic1)
        statisticsDao.insert(statistic2)

        val statistics = statisticsDao.getAll()

        // Should contain both inserted statistics.
        assertEquals(2, statistics.size)
    }

    @Test
    fun insertManyAndGetStatistics() = runTest {
        val statistic1 = StatisticEntity(
            language = "Kotlin",
            percentage = 63.3,
            color = "FFFFF",
            date = LocalDate.of(2024, 1, 1)
        )

        val statistic2 = StatisticEntity(
            language = "Java",
            percentage = 36.7,
            color = "F4F4F4",
            date = LocalDate.of(2024, 1, 1)
        )

        statisticsDao.insertAll(listOf(statistic1, statistic2))

        val statistics = statisticsDao.getAll()

        // Should contain both inserted statistics.
        assertEquals(2, statistics.size)
    }

    @Test
    fun deleteStatistics() = runTest {
        val statistic = StatisticEntity(
            language = "Kotlin",
            percentage = 63.3,
            color = "FFFFF",
            date = LocalDate.of(2024, 1, 1)
        )

        statisticsDao.insert(statistic)

        val statistics = statisticsDao.getAll()

        // Should contain inserted statistic.
        assertEquals(1, statistics.size)

        // Drop the statistic.
        statisticsDao.delete(statistics.first())

        val finalStatistics = statisticsDao.getAll()

        // Should contain no stats.
        assertEquals(0, finalStatistics.size)
    }

    @Test
    fun getByDate() = runTest {
        val initialStatistics = listOf(
            StatisticEntity(
                language = "Kotlin",
                percentage = 63.0,
                color = "FFFFF",
                date = LocalDate.of(2024, 1, 1)
            ),
            StatisticEntity(
                language = "Java",
                percentage = 36.0,
                color = "F4F4F4",
                date = LocalDate.of(2024, 1, 1)
            ),
            StatisticEntity(
                language = "PHP",
                percentage = 1.0,
                color = "F5F5F5",
                date = LocalDate.of(2011, 1, 1)
            )
        )

        statisticsDao.insertAll(initialStatistics)

        val results2024 = statisticsDao.getByDate(LocalDate.of(2024, 1, 1))
        val results2011 = statisticsDao.getByDate(LocalDate.of(2011, 1, 1))

        assertEquals(results2024.size, 2)
        assertEquals(results2011.size, 1)

        assertEquals(listOf("Kotlin", "Java"), results2024.map{it.language})
        assertEquals("PHP", results2011.first().language)
    }

    @Test
    fun serializesAndDeserializesDates() = runTest {
        val date = LocalDate.of(2024, 1, 1)
        val statistic = StatisticEntity(
            language = "Kotlin",
            percentage = 63.3,
            color = "FFFFF",
            date = date
        )

        statisticsDao.insert(statistic)

        val dbStatistic = statisticsDao.getAll().first()

        assertEquals(date, dbStatistic.date)
        assertEquals(statistic.date, dbStatistic.date)
    }
}