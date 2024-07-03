package com.radlance.fooddelivery.domain.usecase.order

import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.repository.OrderRepository

class GetFullCartItemInfoUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(): List<CartItem> {
        return orderRepository.getFullCartItemInfo()
    }
}