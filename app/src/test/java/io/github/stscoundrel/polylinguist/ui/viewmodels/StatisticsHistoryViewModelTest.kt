package io.github.stscoundrel.polylinguist.ui.viewmodels

import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.usecase.GetStatisticsHistoryUseCase
import io.github.stscoundrel.polylinguist.testdata.InMemoryStatisticsRepository
import io.github.stscoundrel.polylinguist.testdata.StatisticFactory
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

val statistics = listOf(
    Statistics(
        date = LocalDate.of(2024, 2, 1),
        statistics = listOf(
            StatisticFactory.createStatistic(language = "Java"),
            StatisticFactory.createStatistic(language = "Kotlin"),
            StatisticFactory.createStatistic(language = "Scala"),
        )
    ),
    Statistics(
        date = LocalDate.of(2024, 2, 2),
        statistics = listOf(
            StatisticFactory.createStatistic(language = "Java"),
            StatisticFactory.createStatistic(language = "Kotlin"),
            StatisticFactory.createStatistic(language = "Scala"),
        )
    )
)


class StatisticsHistoryViewModelTest {
    private fun getViewModel(): StatisticsHistoryViewModel {
        val repository = InMemoryStatisticsRepository(
            currentStatistics = statistics[0],
            latestStatistics = statistics[1]
        )
        repository.setTestStats(statistics)
        val statisticsHistoryViewModel = GetStatisticsHistoryUseCase(repository)

        return StatisticsHistoryViewModel(statisticsHistoryViewModel)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun populatesStatisticsHistoryOnInitTest() = runTest {
        val viewModel = getViewModel()

        // Initially empty stats.
        assertEquals(0, viewModel.history.value.size)

        // Should be loading the initial stats since creation.
        assertTrue(viewModel.isLoading.value)

        // Wait for load to complete, should fetch initial stats.
        advanceUntilIdle()

        // Allow time for CI flakiness.
        Thread.sleep(3000)

        // Should've finished loading.
        assertFalse(viewModel.isLoading.value)

        // Should now contain statistics in the state.
        assertEquals(statistics, viewModel.history.value)
    }
}