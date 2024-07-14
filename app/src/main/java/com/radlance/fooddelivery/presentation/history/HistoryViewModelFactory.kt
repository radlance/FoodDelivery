package com.radlance.fooddelivery.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.radlance.fooddelivery.domain.usecase.history.LoadHistoryUseCase
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class HistoryViewModelFactory(private val token: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[APPLICATION_KEY]

        val historyRepository = (application as ProvideRepository).historyRepository(token)
        val loadHistoryUseCase = LoadHistoryUseCase(historyRepository)

        val mapper = LoadHistoryResultMapper()

        return HistoryViewModel(loadHistoryUseCase, mapper) as T
    }
}