package com.radlance.fooddelivery.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.radlance.fooddelivery.domain.usecase.order.ClearCartUseCase
import com.radlance.fooddelivery.domain.usecase.order.RepeatOrderUseCase
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class CurrentHistoryViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[APPLICATION_KEY]

        val orderRepository = (application as ProvideRepository).orderRepository()
        val repeatOrderUseCase = RepeatOrderUseCase(orderRepository)
        val clearCartUseCase = ClearCartUseCase(orderRepository)

        return CurrentHistoryViewModel(repeatOrderUseCase, clearCartUseCase) as T
    }
}