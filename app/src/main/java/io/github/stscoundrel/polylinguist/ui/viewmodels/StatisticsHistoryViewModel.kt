package io.github.stscoundrel.polylinguist.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.usecase.GetStatisticsHistoryUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatisticsHistoryViewModel(
    private val getStatisticsHistoryUseCase: GetStatisticsHistoryUseCase,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    private val _statistics = MutableStateFlow<List<Statistics>>(listOf())
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val history: StateFlow<List<Statistics>> = _statistics.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        getHistory()
    }

    private fun setIsLoading() {
        _isLoading.value = true
    }

    private fun setIsNotLoading() {
        _isLoading.value = false
    }

    fun updateHistory() {
        setIsLoading()
        _statistics.value = listOf()
        viewModelScope.launch(defaultDispatcher) {
            kotlinx.coroutines.delay(500)
            getHistory()
        }
    }

    private fun getHistory() {
        setIsLoading()
        viewModelScope.launch(defaultDispatcher) {
            val stats = getStatisticsHistoryUseCase()

            _statistics.value = stats
            setIsNotLoading()
        }
    }
}
