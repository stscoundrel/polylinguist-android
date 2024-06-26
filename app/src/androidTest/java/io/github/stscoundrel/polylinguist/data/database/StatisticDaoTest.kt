package io.github.stscoundrel.polylinguist.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import io.github.stscoundrel.polylinguist.testdata.StatisticFactory
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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


        val statistic1 = StatisticFactory.createStatisticEntity(
            language = "Java",
        )

        val statistic2 = StatisticFactory.createStatisticEntity(
            language = "Kotlin",
        )

        statisticsDao.upsert(statistic1)
        statisticsDao.upsert(statistic2)

        val statistics = statisticsDao.getAll()

        // Should contain both inserted statistics.
        assertEquals(2, statistics.size)
    }


    @Test
    fun insertManyAndGetStatistics() = runTest {
        val statistic1 = StatisticFactory.createStatisticEntity(
            language = "Java",
        )

        val statistic2 = StatisticFactory.createStatisticEntity(
            language = "Kotlin",
        )

        statisticsDao.upsertAll(listOf(statistic1, statistic2))

        val statistics = statisticsDao.getAll()

        // Should contain both inserted statistics.
        assertEquals(2, statistics.size)
    }

    @Test
    fun updateStatistics() = runTest {
        val initialStatistic = StatisticFactory.createStatisticEntity(
            language = "Kotlin",
            percentage = 63.3,
            date = LocalDate.of(2024, 1, 1)

        )

        val changedStatistic = StatisticFactory.createStatisticEntity(
            language = "Kotlin",
            percentage = 89.0,
            date = LocalDate.of(2024, 1, 1)
        )

        statisticsDao.upsert(initialStatistic)

        val initialDbStatistics = statisticsDao.getAll()

        // Should have initial stat in db.
        assertEquals(1, initialDbStatistics.size)
        assertEquals(initialStatistic, initialDbStatistics.first())

        // Update the changed stat. Should replace original due to
        // date & language being same.
        statisticsDao.upsert(changedStatistic)
        val dbStatistics = statisticsDao.getAll()

        // Should have updated initial DB stat, not created a new one.
        assertEquals(1, dbStatistics.size)
        assertEquals(changedStatistic, dbStatistics.first())
    }

    @Test
    fun updateManyStatistics() = runTest {
        val statistic1 = StatisticFactory.createStatisticEntity(
            language = "Kotlin",
            percentage = 63.3,
            size = 1260,
            date = LocalDate.of(2024, 1, 1)
        )

        val statistic2 = StatisticFactory.createStatisticEntity(
            language = "Java",
            percentage = 36.7,
            size = 734,
            date = LocalDate.of(2024, 1, 1)
        )

        statisticsDao.upsertAll(listOf(statistic1, statistic2))
        val initialDbStatistics = statisticsDao.getAll()

        // Should have initial stats in db.
        assertEquals(2, initialDbStatistics.size)

        // Update both stats.
        val changedStatistics = listOf(
            statistic1.copy(size = 666, percentage = 66.6),
            statistic2.copy(size = 333, percentage = 33.3)
        )
        // Update the changed stats. Should replace originals.
        statisticsDao.upsertAll(changedStatistics)
        val dbStatistics = statisticsDao.getAll()

        // Should have updated initial DB stat, not created a new one.
        assertEquals(2, dbStatistics.size)
        assertEquals(changedStatistics, dbStatistics)
    }

    @Test
    fun deleteStatistics() = runTest {
        val statistic = StatisticFactory.createStatisticEntity(
            language = "Kotlin",
        )

        statisticsDao.upsert(statistic)

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
            StatisticFactory.createStatisticEntity(
                language = "Kotlin",
                date = LocalDate.of(2024, 1, 1)
            ),
            StatisticFactory.createStatisticEntity(
                language = "Java",
                date = LocalDate.of(2024, 1, 1)
            ),
            StatisticFactory.createStatisticEntity(
                language = "PHP",
                date = LocalDate.of(2011, 1, 1)
            )
        )

        statisticsDao.upsertAll(initialStatistics)

        val results2024 = statisticsDao.getByDate(LocalDate.of(2024, 1, 1))
        val results2011 = statisticsDao.getByDate(LocalDate.of(2011, 1, 1))

        assertEquals(results2024.size, 2)
        assertEquals(results2011.size, 1)

        assertTrue(listOf("Kotlin", "Java").containsAll(results2024.map { it.language }))
        assertEquals("PHP", results2011.first().language)
    }

    @Test
    fun getLatestDate() = runTest {
        val firstOfJanuary = LocalDate.of(2013, 1, 1)
        val secondOfJanuary = LocalDate.of(2013, 1, 2)
        val statistics = listOf(
            StatisticFactory.createStatisticEntity(
                language = "Kotlin",
                date = firstOfJanuary
            ),
            StatisticFactory.createStatisticEntity(
                language = "Java",
                date = firstOfJanuary
            ),
            StatisticFactory.createStatisticEntity(
                language = "PHP",
                date = secondOfJanuary
            ),
            StatisticFactory.createStatisticEntity(
                language = "Perl",
                date = secondOfJanuary
            )
        )

        statisticsDao.upsertAll(statistics)

        val result = statisticsDao.getLatestDate()

        // Second of January is the latest date in data.
        assertEquals(secondOfJanuary, result)
    }

    @Test
    fun getLatestStatistics() = runTest {
        val firstOfJanuary = LocalDate.of(2013, 1, 1)
        val secondOfJanuary = LocalDate.of(2013, 1, 2)
        val statistics = listOf(
            StatisticFactory.createStatisticEntity(
                language = "Kotlin",
                date = firstOfJanuary
            ),
            StatisticFactory.createStatisticEntity(
                language = "Java",
                date = firstOfJanuary
            ),
            StatisticFactory.createStatisticEntity(
                language = "PHP",
                date = secondOfJanuary
            ),
            StatisticFactory.createStatisticEntity(
                language = "Perl",
                date = secondOfJanuary
            )
        )

        statisticsDao.upsertAll(statistics)

        val result = statisticsDao.getLatestStatistics()

        // Two latter entities from 2nd of January
        // should be the latest.
        val expected = listOf(
            statistics[2],
            statistics[3]
        )

        assertEquals(expected, result)
    }

    @Test
    fun serializesAndDeserializesDates() = runTest {
        val date = LocalDate.of(2024, 1, 1)
        val statistic = StatisticFactory.createStatisticEntity(
            language = "Kotlin",
            date = date
        )

        statisticsDao.upsert(statistic)

        val dbStatistic = statisticsDao.getAll().first()

        assertEquals(date, dbStatistic.date)
        assertEquals(statistic.date, dbStatistic.date)
    }
}