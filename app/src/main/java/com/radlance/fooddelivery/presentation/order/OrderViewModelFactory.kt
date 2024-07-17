package com.radlance.fooddelivery.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.radlance.fooddelivery.domain.usecase.order.ClearCartUseCase
import com.radlance.fooddelivery.domain.usecase.order.CreateDeliveryUseCase
import com.radlance.fooddelivery.domain.usecase.order.DeleteCartItemUseCase
import com.radlance.fooddelivery.domain.usecase.order.GetFullCartItemInfoUseCase
import com.radlance.fooddelivery.domain.usecase.order.GetTotalOrderCostUseCase
import com.radlance.fooddelivery.domain.usecase.order.UpdateCartItemUseCase
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class OrderViewModelFactory(private val token: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[APPLICATION_KEY]
        val orderRepository = (application as ProvideRepository).orderRepository(token)

        val getFullCartItemInfoUseCase = GetFullCartItemInfoUseCase(orderRepository)
        val updateCartItemUseCase = UpdateCartItemUseCase(orderRepository)
        val getTotalOrderCostUseCase = GetTotalOrderCostUseCase(orderRepository)
        val deleteCartItemUseCase = DeleteCartItemUseCase(orderRepository)
        val createDeliveryUseCase = CreateDeliveryUseCase(orderRepository)
        val clearCartUseCase = ClearCartUseCase(orderRepository)

        val mapper = DeliveryResultMapper()
        return OrderViewModel(
            getFullCartItemInfoUseCase,
            updateCartItemUseCase,
            getTotalOrderCostUseCase,
            deleteCartItemUseCase,
            createDeliveryUseCase,
            clearCartUseCase,
            mapper
        ) as T
    }
}