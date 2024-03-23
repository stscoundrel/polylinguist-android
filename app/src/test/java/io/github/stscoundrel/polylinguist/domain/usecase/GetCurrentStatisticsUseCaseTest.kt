package io.github.stscoundrel.polylinguist.domain.usecase

import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.testdata.InMemoryStatisticsRepository
import io.github.stscoundrel.polylinguist.testdata.StatisticFactory
import io.github.stscoundrel.polylinguist.testdata.StatisticsFactory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

val todaysStatistics = Statistics(
    date = LocalDate.now(),
    statistics = listOf(
        StatisticFactory.createStatistic(language = "Java"),
        StatisticFactory.createStatistic(language = "Kotlin"),
    )
)

class GetCurrentStatisticsUseCaseTest {
    private lateinit var repository: InMemoryStatisticsRepository

    @Before
    fun initRepository() {
        repository = InMemoryStatisticsRepository(
            currentStatistics = todaysStatistics,
            latestStatistics = StatisticsFactory.createStatistics()
        )
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