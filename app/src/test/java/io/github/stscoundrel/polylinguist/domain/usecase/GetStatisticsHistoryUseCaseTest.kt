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


class GetStatisticsHistoryUseCaseTest {
    private lateinit var repository: InMemoryStatisticsRepository

    @Before
    fun initRepository() {
        repository = InMemoryStatisticsRepository(
            latestStatistics = StatisticsFactory.createStatistics(),
            currentStatistics = StatisticsFactory.createStatistics()
        )

        // Set stat to be found in repository.
        repository.setTestStats(
            listOf(
                Statistics(
                    date = LocalDate.of(2024, 3, 1),
                    statistics = listOf(
                        StatisticFactory.createStatistic("Dart")
                    )
                ),
            )
        )
    }

    @Test
    fun getsStatisticsHistory() = runBlocking {
        val useCase = GetStatisticsHistoryUseCase(repository)

        val history = useCase()

        // History should contain stats for one date only.
        assertEquals(1, history.size)
        assertEquals(LocalDate.of(2024, 3, 1), history.first().date)
        assertEquals("Dart", history.first().statistics.first().language)
    }
}