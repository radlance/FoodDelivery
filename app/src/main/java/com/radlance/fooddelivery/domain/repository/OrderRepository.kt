package com.radlance.fooddelivery.domain.repository

import com.radlance.fooddelivery.domain.entity.CartItem

interface OrderRepository {
    suspend fun getFullCartItemInfo(): List<CartItem>
    suspend fun updateCartItem(cartItem: CartItem)
    suspend fun getTotalOrderCost(): Double
    suspend fun deleteCartItem(cartItem: CartItem)
}