package io.github.stscoundrel.polylinguist.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.stscoundrel.polylinguist.domain.Statistics
import io.github.stscoundrel.polylinguist.domain.usecase.GetCurrentStatisticsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrentStatisticsViewModel(
    private val getCurrentStatisticsUseCase: GetCurrentStatisticsUseCase,
    private val populateOnInit: Boolean = true,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {
    val currentStatistics = mutableStateOf<Statistics?>(null)

    init {
        if (populateOnInit) {
            getStatistics()
        }
    }

    fun getStatistics() {
        viewModelScope.launch(defaultDispatcher) {
            val latestStatistics = getCurrentStatisticsUseCase()

            currentStatistics.value = latestStatistics
        }
    }
}
