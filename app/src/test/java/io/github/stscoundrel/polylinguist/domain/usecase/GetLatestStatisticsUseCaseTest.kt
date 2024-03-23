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

val latestStatistics = Statistics(
    date = LocalDate.now(),
    statistics = listOf(
        StatisticFactory.createStatistic(language = "Java"),
        StatisticFactory.createStatistic(language = "Kotlin"),
    )
)


class GetLatestStatisticsUseCaseTest {
    private lateinit var repository: InMemoryStatisticsRepository

    @Before
    fun initRepository() {
        repository = InMemoryStatisticsRepository(
            latestStatistics = latestStatistics,
            currentStatistics = StatisticsFactory.createStatistics()
        )
    }

    @Test
    fun getsLatestStatistics() = runBlocking {
        val useCase = GetLatestStatisticsUseCase(repository)

        val statistics = useCase()

        assertEquals(latestStatistics, statistics)
    }
}