package io.github.stscoundrel.polylinguist.domain.usecase

import io.github.stscoundrel.polylinguist.domain.Statistic
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.StatisticsRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

val todaysStatistics = Statistics(
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

class InMemoryStatisticsRepository : StatisticsRepository {
    val statistics: MutableMap<LocalDate, List<Statistic>> = mutableMapOf()

    override suspend fun getCurrent(): Statistics {
        return todaysStatistics
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

        // Should've stored them for later use with todays date.
        assertEquals(repository.statistics.get(LocalDate.now()), currentStatistics.statistics)
    }
}