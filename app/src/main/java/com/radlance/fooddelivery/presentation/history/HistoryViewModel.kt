package com.radlance.fooddelivery.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.core.LoadHistoryResult
import com.radlance.fooddelivery.domain.usecase.history.LoadHistoryUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val loadHistoryUseCase: LoadHistoryUseCase,
    private val mapper: LoadHistoryResult.Mapper<LoadHistoryState>
) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _history = MutableLiveData<LoadHistoryState>()
    val history: LiveData<LoadHistoryState>
        get() = _history

    fun loadHistory() {
        viewModelScope.launch {
            _history.value = LoadHistoryState.Loading
            val loadResult = loadHistoryUseCase()
            _history.value = loadResult.map(mapper)
        }
    }
}