package io.github.stscoundrel.polylinguist.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.github.stscoundrel.polylinguist.data.database.AppDatabase
import io.github.stscoundrel.polylinguist.data.database.StatisticDao
import io.github.stscoundrel.polylinguist.data.network.NetworkStatistic
import io.github.stscoundrel.polylinguist.data.network.StatisticsService
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

val networkStatistics: List<NetworkStatistic> = listOf(
    NetworkStatistic(
        language= "Java",
        percentage = 66.6,
        color = "#F3F3F3"
    ),
    NetworkStatistic(
        language= "Kotlin",
        percentage = 33.3,
        color = "#F4F4F4"
    ),
)

class InMemoryStatisticsService: StatisticsService {
    // Test double for statistics service.
    override suspend fun getCurrentStatistics(): List<NetworkStatistic> {
        return networkStatistics
    }
}

class StatisticsRepositoryTest {
    private lateinit var statisticDao: StatisticDao
    private lateinit var repository: StatisticsRepository

    @Before
    fun initRepository() {
        // Setup repository with in-memory database and test double for network service.

        val database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        statisticDao = database.statisticsDao()
        repository = StatisticsRepository(InMemoryStatisticsService(), statisticDao)
    }

    @Test
    fun getCurrentTest() = runBlocking {
        val expected = Statistics(
            date = LocalDate.now(),
            statistics = listOf(
                Statistic(
                    language = "Java",
                    percentage = 66.6,
                    color = "#F3F3F3"
                ),
                Statistic(
                    language= "Kotlin",
                    percentage = 33.3,
                    color = "#F4F4F4"
                )
            )
        )

        val result = repository.getCurrent()

        // Should've fetched data & transformed to data model
        Assert.assertEquals(expected, result)
    }

    @Test
    fun saveTest() = runBlocking {
        val statistics = Statistics(
            date = LocalDate.of(1989, 7, 30),
            statistics = listOf(
                Statistic(
                    language = "Cobol",
                    color = "00000",
                    percentage = 50.0,
                ),
                Statistic(
                    language = "Fortran",
                    color = "FFFFFF",
                    percentage = 50.0,
                ),
            )
        )
        val result = repository.save(
            statistics
        )

        // Should've inserted current statistics to database.
        val dbRows = statisticDao.getAll()

        // Rows should contain expected data.
        val firstStat = dbRows.first()
        Assert.assertEquals("Cobol", firstStat.language)
        Assert.assertEquals(50.0, firstStat.percentage, 0.0)
        Assert.assertEquals(statistics.date, firstStat.date)


        val secondStat = dbRows.elementAt(1)
        Assert.assertEquals("Fortran", secondStat.language)
        Assert.assertEquals(50.0, secondStat.percentage, 0.0)
        Assert.assertEquals(statistics.date, firstStat.date)
    }
}