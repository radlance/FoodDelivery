package com.radlance.fooddelivery.domain.repository

import com.radlance.fooddelivery.domain.core.DeliveryResult
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.entity.Delivery

interface OrderRepository {
    suspend fun getFullCartItemInfo(): List<CartItem>
    suspend fun updateCartItem(cartItem: CartItem)
    suspend fun getTotalOrderCost(): Double
    suspend fun deleteCartItem(cartItem: CartItem)
    suspend fun clearCart()
    suspend fun createDelivery(delivery: Delivery): DeliveryResult
    suspend fun repeatOrder(order: List<CartItem>)
}