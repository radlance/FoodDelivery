package com.radlance.fooddelivery.domain.repository

import com.radlance.fooddelivery.domain.entity.CartItem

interface OrderRepository {
    suspend fun getFullCartItemInfo(): List<CartItem>
}