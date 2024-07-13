package com.radlance.fooddelivery.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.radlance.fooddelivery.domain.usecase.order.GetFullCartItemInfoUseCase
import com.radlance.fooddelivery.domain.usecase.order.UpdateCartItemUseCase
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class OrderViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[APPLICATION_KEY]
        val orderRepository = (application as ProvideRepository).orderRepository()
        val getFullCartItemInfoUseCase = GetFullCartItemInfoUseCase(orderRepository)
        val updateCartItemUseCase = UpdateCartItemUseCase(orderRepository)
        return OrderViewModel(getFullCartItemInfoUseCase, updateCartItemUseCase) as T
    }
}