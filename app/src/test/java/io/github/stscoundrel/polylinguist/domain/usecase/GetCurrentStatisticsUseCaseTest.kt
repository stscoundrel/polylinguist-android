package io.github.stscoundrel.polylinguist.domain.usecase

import io.github.stscoundrel.polylinguist.domain.Statistic
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.StatisticsRepository
import io.github.stscoundrel.polylinguist.testdata.StatisticsFactory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

val todaysStatistics = Statistics(
    date = LocalDate.now(),
    statistics = listOf(
        StatisticsFactory.createStatistic(language = "Java"),
        StatisticsFactory.createStatistic(language = "Kotlin"),
    )
)

class InMemoryStatisticsRepository : StatisticsRepository {
    val statistics: MutableMap<LocalDate, List<Statistic>> = mutableMapOf()

    override suspend fun getCurrent(): Statistics {
        return todaysStatistics
    }

    override suspend fun getLatest(): Statistics {
        TODO("Not yet implemented")
    }

    override suspend fun getByDate(date: LocalDate): Statistics {
        TODO("Not yet implemented")
    }

    override suspend fun save(statistic: Statistics) {
        statistics.put(statistic.date, statistic.statistics)
    }

}

class GetCurrentStatisticsUseCaseTest {
    private lateinit var repository: InMemoryStatisticsRepository

    @Before
    fun initRepository() {
        repository = InMemoryStatisticsRepository()
    }

    @Test
    fun getsAndStoresCurrentStatistics() = runBlocking {
        val useCase = GetCurrentStatisticsUseCase(repository)

        val currentStatistics = useCase()

        // Should've returned todays stats.
        assertEquals(todaysStatistics, currentStatistics)

        // Should've stored them for later use with today's date.
        assertEquals(repository.statistics.get(LocalDate.now()), currentStatistics.statistics)
    }
}