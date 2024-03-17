package io.github.stscoundrel.polylinguist.ui

import io.github.stscoundrel.polylinguist.domain.Statistic
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.StatisticsRepository
import io.github.stscoundrel.polylinguist.domain.usecase.GetCurrentStatisticsUseCase
import io.github.stscoundrel.polylinguist.domain.usecase.GetLatestStatisticsUseCase
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

val initialStatistics = Statistics(
    date = LocalDate.now(),
    statistics = listOf(
        Statistic(
            language = "Java",
            percentage = 66.0,
            size = 1332,
            color = "#F3F3F3"
        ),
        Statistic(
            language = "Kotlin",
            percentage = 33.0,
            size = 666,
            color = "#F4F4F4"
        ),
        Statistic(
            language = "Scala",
            percentage = 1.0,
            size = 1,
            color = "#F4F4F4"
        ),
    )
)

class InMemoryStatisticsRepository : StatisticsRepository {
    val statistics: MutableMap<LocalDate, List<Statistic>> = mutableMapOf()

    override suspend fun getCurrent(): Statistics {
        return todaysStatistics
    }

    override suspend fun getLatest(): Statistics {
        return initialStatistics
    }

    override suspend fun getByDate(date: LocalDate): Statistics {
        TODO("Not yet implemented")
    }

    override suspend fun save(statistic: Statistics) {
        statistics.put(statistic.date, statistic.statistics)
    }

}

class CurrentStatisticsViewModelTest {
    private lateinit var currentStatsUseCase: GetCurrentStatisticsUseCase
    private lateinit var latestStatsUseCase: GetLatestStatisticsUseCase

    @Before
    fun initRepository() {
        currentStatsUseCase = GetCurrentStatisticsUseCase(InMemoryStatisticsRepository())
        latestStatsUseCase = GetLatestStatisticsUseCase(InMemoryStatisticsRepository())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun populatesInitialStatisticsTest() = runTest {
        val viewModel = CurrentStatisticsViewModel(currentStatsUseCase, latestStatsUseCase)

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
        val viewModel = CurrentStatisticsViewModel(currentStatsUseCase, latestStatsUseCase)

        // Trigger update
        viewModel.getCurrentStatistics()

        // Should be loading
        assertTrue(viewModel.isLoading.value)

        // Should still be empty, as fetch is happening in background thread.
        assertEquals(null, viewModel.statistics.value)

        // Wait for coroutine to complete.
        advanceUntilIdle()

        // Allow time for CI flakiness.
        Thread.sleep(3000)

        // Should've finished loading
        assertFalse(viewModel.isLoading.value)

        // Should now contain statistics in the state.
        assertEquals(todaysStatistics, viewModel.statistics.value)
    }

}