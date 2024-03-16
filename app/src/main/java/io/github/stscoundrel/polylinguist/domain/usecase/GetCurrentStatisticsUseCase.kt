package io.github.stscoundrel.polylinguist.domain.usecase

import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.StatisticsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCurrentStatisticsUseCase(
    private val statisticsRepository: StatisticsRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): Statistics = withContext(defaultDispatcher) {
        val currentStats = statisticsRepository.getCurrent()
        statisticsRepository.save(currentStats)

        return@withContext currentStats
    }
}