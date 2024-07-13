package com.radlance.fooddelivery.domain.usecase.order

import com.radlance.fooddelivery.domain.repository.OrderRepository

class GetTotalOrderCostUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(): Double {
        return orderRepository.getTotalOrderCost()
    }
}