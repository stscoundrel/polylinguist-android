package io.github.stscoundrel.polylinguist.ui.viewmodels

import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.usecase.GetCurrentStatisticsUseCase
import io.github.stscoundrel.polylinguist.domain.usecase.GetLatestStatisticsUseCase
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

val todaysStatistics = Statistics(
    date = LocalDate.now(),
    statistics = listOf(
        StatisticFactory.createStatistic(language = "Java"),
        StatisticFactory.createStatistic(language = "Kotlin"),
    )
)

val initialStatistics = Statistics(
    date = LocalDate.of(2024, 2, 1),
    statistics = listOf(
        StatisticFactory.createStatistic(language = "Java"),
        StatisticFactory.createStatistic(language = "Kotlin"),
        StatisticFactory.createStatistic(language = "Scala"),
    )
)


class CurrentStatisticsViewModelTest {
    private fun getViewModel(): CurrentStatisticsViewModel {
        val repository = InMemoryStatisticsRepository(
            currentStatistics = todaysStatistics,
            latestStatistics = initialStatistics
        )
        val currentStatsUseCase = GetCurrentStatisticsUseCase(repository)
        val latestStatsUseCase = GetLatestStatisticsUseCase(repository)

        return CurrentStatisticsViewModel(currentStatsUseCase, latestStatsUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun populatesInitialStatisticsTest() = runTest {
        val viewModel = getViewModel()

        // Initially empty stats.
        assertEquals(null, viewModel.statistics.value)

        // Should be loading the initial stats since creation.
        assertTrue(viewModel.isLoading.value)

        // Wait for load to complete, should fetch initial stats.
        advanceUntilIdle()

        // Allow time for CI flakiness.
        Thread.sleep(3000)

        // Should've finished loading.
        assertFalse(viewModel.isLoading.value)

        // Should now contain statistics in the state.
        assertEquals(initialStatistics, viewModel.statistics.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun populatesCurrentStatisticsTest() = runTest {
        val viewModel = getViewModel()

        // Get initial load out of the way.
        advanceUntilIdle()
        Thread.sleep(3000)

        // Trigger update
        viewModel.getCurrentStatistics()

        // Should be loading
        assertTrue(viewModel.isLoading.value)

        // Wait for coroutine to complete.
        advanceUntilIdle()

        // Allow time for CI flakiness.
        Thread.sleep(3000)

        // Should've finished loading
        assertFalse(viewModel.isLoading.value)

        // Should now contain statistics in the state.
        assertEquals(todaysStatistics, viewModel.statistics.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun hasLatestStatisticsTest() = runTest {
        val viewModel = getViewModel()

        // Wait for load to complete, should fetch initial stats.
        advanceUntilIdle()

        // Should not have latest stats out-of-the-box.
        val initialResult = viewModel.hasUpToDateStatistics()

        assertFalse(initialResult)

        // Trigger update
        viewModel.getCurrentStatistics()
        advanceUntilIdle()

        // Allow time for CI flakiness.
        Thread.sleep(3000)

        // Should now have latest stats.
        val result = viewModel.hasUpToDateStatistics()

        assertTrue(result)
    }

}