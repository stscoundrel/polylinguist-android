package io.github.stscoundrel.polylinguist.domain.usecase


import io.github.stscoundrel.polylinguist.domain.Statistic
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.StatisticsRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

val latestStatistics = Statistics(
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