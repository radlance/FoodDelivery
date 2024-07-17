package com.radlance.fooddelivery.domain.usecase.order

import com.radlance.fooddelivery.domain.repository.OrderRepository

class ClearCartUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke() {
        orderRepository.clearCart()
    }
}