package io.github.stscoundrel.polylinguist.data


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.github.stscoundrel.polylinguist.data.database.AppDatabase
import io.github.stscoundrel.polylinguist.data.database.StatisticDao
import io.github.stscoundrel.polylinguist.data.database.StatisticEntity
import io.github.stscoundrel.polylinguist.data.network.NetworkStatistic
import io.github.stscoundrel.polylinguist.data.network.StatisticsService
import io.github.stscoundrel.polylinguist.domain.Statistic
import io.github.stscoundrel.polylinguist.domain.Statistics
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

val networkStatistics: List<NetworkStatistic> = listOf(
    NetworkStatistic(
        language = "Java",
        percentage = 66.6,
        size = 1332,
        color = "#F3F3F3"
    ),
    NetworkStatistic(
        language = "Kotlin",
        percentage = 33.3,
        size = 666,
        color = "#F4F4F4"
    ),
)

class InMemoryStatisticsService : StatisticsService {
    // Test double for statistics service.
    override suspend fun getCurrentStatistics(): List<NetworkStatistic> {
        return networkStatistics
    }
}

class StatisticsRepositoryTest {
    private lateinit var statisticDao: StatisticDao
    private lateinit var repository: DefaultStatisticsRepository

    @Before
    fun initRepository() {
        // Setup repository with in-memory database and test double for network service.

        val database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        statisticDao = database.statisticsDao()
        repository = DefaultStatisticsRepository(InMemoryStatisticsService(), statisticDao)
    }

    @Test
    fun getCurrentTest() = runBlocking {
        val expected = Statistics(
            date = LocalDate.now(),
            statistics = listOf(
                Statistic(
                    language = "Java",
                    percentage = 66.6,
                    size = 1332,
                    color = "#F3F3F3"
                ),
                Statistic(
                    language = "Kotlin",
                    percentage = 33.3,
                    size = 666,
                    color = "#F4F4F4"
                )
            )
        )

        val result = repository.getCurrent()

        // Should've fetched data & transformed to data model
        assertEquals(expected, result)
    }

    @Test
    fun getLatestWhenNoneKnownTest() = runBlocking {
        val result = repository.getLatest()

        assertNull(result)
    }

    @Test
    fun getLatestTest() = runBlocking {
        val date2012 = LocalDate.of(2012, 1, 1)
        val date2020 = LocalDate.of(2020, 1, 1)
        val date2024 = LocalDate.of(2024, 1, 1)

        val initialStatistics = listOf(
            StatisticEntity(
                language = "Kotlin",
                percentage = 63.0,
                size = 1332,
                color = "FFFFF",
                date = date2012
            ),
            StatisticEntity(
                language = "Java",
                percentage = 36.0,
                size = 666,
                color = "F4F4F4",
                date = date2012
            ),
            StatisticEntity(
                language = "Scala",
                percentage = 63.0,
                size = 1332,
                color = "FFFFF",
                date = date2020
            ),
            StatisticEntity(
                language = "Clojure",
                percentage = 36.0,
                size = 666,
                color = "F4F4F4",
                date = date2020
            ),
            StatisticEntity(
                language = "Python",
                percentage = 63.0,
                size = 1332,
                color = "FFFFF",
                date = date2024
            ),
            StatisticEntity(
                language = "Golang",
                percentage = 36.0,
                size = 666,
                color = "F4F4F4",
                date = date2024
            ),
        )

        statisticDao.upsertAll(initialStatistics)

        val result = repository.getLatest()

        assertNotNull(result)

        if (result != null) {
            assertEquals(result.date, date2024)
            assertEquals(result.statistics.size, 2)

            assertTrue(
                listOf(
                    "Python",
                    "Golang"
                ).containsAll(result.statistics.map { it.language })
            )
        }

    }

    @Test
    fun getByDateTest() = runBlocking {
        val date = LocalDate.of(2024, 1, 1)

        // Populate given days stats to be found in database.
        val initialStatistics = listOf(
            StatisticEntity(
                language = "Kotlin",
                percentage = 63.0,
                size = 1332,
                color = "FFFFF",
                date = date
            ),
            StatisticEntity(
                language = "Java",
                percentage = 36.0,
                size = 666,
                color = "F4F4F4",
                date = date
            ),
        )

        statisticDao.upsertAll(initialStatistics)

        val result = repository.getByDate(date)

        assertEquals(result.date, date)
        assertEquals(result.statistics.size, 2)

        // Get for another day, which has no entries.
        val emptyDate = LocalDate.of(2020, 1, 1)
        val emptyResult = repository.getByDate(emptyDate)

        assertEquals(emptyResult.date, emptyDate)
        assertEquals(emptyResult.statistics.size, 0)
    }

    @Test
    fun saveTest() = runBlocking {
        val statistics = Statistics(
            date = LocalDate.of(1989, 7, 30),
            statistics = listOf(
                Statistic(
                    language = "Cobol",
                    color = "00000",
                    size = 1000,
                    percentage = 50.0,
                ),
                Statistic(
                    language = "Fortran",
                    color = "FFFFFF",
                    size = 1000,
                    percentage = 50.0,
                ),
            )
        )
        repository.save(
            statistics
        )

        // Should've inserted current statistics to database.
        val dbRows = statisticDao.getAll()

        // Rows should contain expected data.
        val firstStat = dbRows.first()
        assertEquals("Cobol", firstStat.language)
        assertEquals(50.0, firstStat.percentage, 0.0)
        assertEquals(statistics.date, firstStat.date)


        val secondStat = dbRows.elementAt(1)
        assertEquals("Fortran", secondStat.language)
        assertEquals(50.0, secondStat.percentage, 0.0)
        assertEquals(statistics.date, firstStat.date)
    }
}