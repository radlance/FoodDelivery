package com.radlance.fooddelivery.data.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.database.DeliveryDao
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.OrderRepository

class OrderRepositoryImpl(private val service: Service, private val deliveryDao: DeliveryDao) :
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
}