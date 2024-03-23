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

val latestStatistics = Statistics(
    date = LocalDate.now(),
    statistics = listOf(
        StatisticsFactory.createStatistic(language = "Java"),
        StatisticsFactory.createStatistic(language = "Kotlin"),
    )
)

class InMemoryDummyStatisticsRepository : StatisticsRepository {
    val statistics: MutableMap<LocalDate, List<Statistic>> = mutableMapOf()

    override suspend fun getCurrent(): Statistics {
        TODO("Not yet implemented")
    }

    override suspend fun getLatest(): Statistics {
        return latestStatistics
    }

    override suspend fun getByDate(date: LocalDate): Statistics {
        TODO("Not yet implemented")
    }

    override suspend fun save(statistic: Statistics) {
        statistics.put(statistic.date, statistic.statistics)
    }

}

class GetLatestStatisticsUseCaseTest {
    private lateinit var repository: InMemoryDummyStatisticsRepository

    @Before
    fun initRepository() {
        repository = InMemoryDummyStatisticsRepository()
    }

    @Test
    fun getsLatestStatistics() = runBlocking {
        val useCase = GetLatestStatisticsUseCase(repository)

        val statistics = useCase()

        assertEquals(latestStatistics, statistics)
    }
}