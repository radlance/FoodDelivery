package com.radlance.fooddelivery.domain.usecase.order

import com.radlance.fooddelivery.domain.core.DeliveryResult
import com.radlance.fooddelivery.domain.entity.Delivery
import com.radlance.fooddelivery.domain.repository.OrderRepository

class CreateDeliveryUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(delivery: Delivery): DeliveryResult {
        return orderRepository.createDelivery(delivery)
    }
}