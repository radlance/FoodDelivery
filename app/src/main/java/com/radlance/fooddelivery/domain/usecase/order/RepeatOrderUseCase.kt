package com.radlance.fooddelivery.domain.usecase.order

import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.repository.OrderRepository

class RepeatOrderUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(order: List<CartItem>) {
        orderRepository.repeatOrder(order)
    }
}