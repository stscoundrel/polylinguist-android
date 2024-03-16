package io.github.stscoundrel.polylinguist.ui

import io.github.stscoundrel.polylinguist.domain.Statistic
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.StatisticsRepository
import io.github.stscoundrel.polylinguist.domain.usecase.GetCurrentStatisticsUseCase
import io.github.stscoundrel.polylinguist.ui.theme.CurrentStatisticsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

// TODO: these are shared with use case test class. Combine them to eventual factory/similar.
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

class CurrentStatisticsViewModelTest {
    private lateinit var useCase: GetCurrentStatisticsUseCase

    @Before
    fun initRepository() {
        useCase = GetCurrentStatisticsUseCase(InMemoryStatisticsRepository())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun populatesCurrentStatistics() = runTest {
        val viewModel = CurrentStatisticsViewModel(useCase, false)

        // Initially empty stats.
        assertEquals(null, viewModel.currentStatistics.value)

        // Trigger update
        viewModel.getStatistics()

        // Should still be empty, as fetch is happening in background thread.
        assertEquals(null, viewModel.currentStatistics.value)

        // Wait for coroutine to complete.
        advanceUntilIdle()

        // CI seems to be flaky here. Just add extra wait for it to catch up.
        Thread.sleep(3000)

        // Should now contain statistics in the state.
        assertEquals(todaysStatistics, viewModel.currentStatistics.value)
    }

}