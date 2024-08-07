package io.github.stscoundrel.polylinguist.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.usecase.GetCurrentStatisticsUseCase
import io.github.stscoundrel.polylinguist.domain.usecase.GetLatestStatisticsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CurrentStatisticsViewModel(
    private val getCurrentStatisticsUseCase: GetCurrentStatisticsUseCase,
    private val getLatestStatisticsUseCase: GetLatestStatisticsUseCase,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _statistics = MutableStateFlow<Statistics?>(null)
    private val _comparison = MutableStateFlow<Statistics?>(null)
    private val _isLoading = MutableStateFlow<Boolean>(false)
    private val currentDate = LocalDate.now()
    val statistics: StateFlow<Statistics?> = _statistics.asStateFlow()
    val comparison: StateFlow<Statistics?> = _comparison.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        getLatestKnownStatistics()
    }

    private fun setIsLoading() {
        _isLoading.value = true
    }

    private fun setIsNotLoading() {
        _isLoading.value = false
    }

    fun hasUpToDateStatistics(): Boolean {
        if (statistics.value != null) {
            return statistics.value!!.date == currentDate
        }

        return false
    }

    private fun getLatestKnownStatistics() {
        setIsLoading()
        viewModelScope.launch(defaultDispatcher) {
            val latestStatistics = getLatestStatisticsUseCase()

            _statistics.value = latestStatistics
            setIsNotLoading()
        }
    }

    fun getCurrentStatistics() {
        setIsLoading()

        viewModelScope.launch(defaultDispatcher) {
            val currentStatistics = getCurrentStatisticsUseCase()

            // Preserve the previous stats as comparison value
            // for the newly fetched stats.
            _comparison.value = _statistics.value
            _statistics.value = currentStatistics
            setIsNotLoading()
        }
    }
}
