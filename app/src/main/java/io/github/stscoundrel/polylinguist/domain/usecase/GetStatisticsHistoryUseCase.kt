package io.github.stscoundrel.polylinguist.domain.usecase

import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.StatisticsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate


class GetStatisticsHistoryUseCase(
    private val statisticsRepository: StatisticsRepository,
    private val startDate: LocalDate = LocalDate.of(2024, 1, 1),
    private val endDate: LocalDate = LocalDate.now(),
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): List<Statistics> = withContext(defaultDispatcher) {
        return@withContext statisticsRepository.getHistory(startDate, endDate)
    }
}