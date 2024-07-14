package com.radlance.fooddelivery.data.repository

import com.radlance.fooddelivery.data.database.CartItemCache
import com.radlance.fooddelivery.data.database.DeliveryDao
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.OrderRepository

class OrderRepositoryImpl(private val deliveryDao: DeliveryDao) :
    OrderRepository {
    override suspend fun getFullCartItemInfo(): List<CartItem> {
        return deliveryDao.getFullCartItemInfo().map {
            CartItem(
                it.cartItem.count,
                Product(
                    it.product.id,
                    it.product.title,
                    it.product.price,
                    it.product.imageUrl,
                    it.product.categoryId
                )
            )
        }
    }

    override suspend fun updateCartItem(cartItem: CartItem) {
        deliveryDao.updateCartItem(CartItemCache(cartItem.product.id, cartItem.count))
    }

    override suspend fun getTotalOrderCost(): Double {
        return deliveryDao.getTotalOrderCost()
    }

    override suspend fun deleteCartItem(cartItem: CartItem) {
        deliveryDao.deleteCartItem(CartItemCache(cartItem.product.id, cartItem.count))
    }
}