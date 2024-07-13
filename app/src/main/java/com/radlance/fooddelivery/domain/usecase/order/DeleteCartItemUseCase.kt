package com.radlance.fooddelivery.domain.usecase.order

import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.repository.OrderRepository

class DeleteCartItemUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(cartItem: CartItem) {
        orderRepository.deleteCartItem(cartItem)
    }
}