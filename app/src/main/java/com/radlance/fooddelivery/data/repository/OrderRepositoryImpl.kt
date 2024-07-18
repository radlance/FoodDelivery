package com.radlance.fooddelivery.data.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.api.request.DeliveryRequest
import com.radlance.fooddelivery.data.api.response.CartItemResponse
import com.radlance.fooddelivery.data.database.CartItemCache
import com.radlance.fooddelivery.data.database.DeliveryDao
import com.radlance.fooddelivery.domain.core.DeliveryResult
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.entity.Delivery
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.OrderRepository
import retrofit2.HttpException

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

    override suspend fun updateCartItem(cartItem: CartItem) {
        deliveryDao.updateCartItem(CartItemCache(cartItem.product.id, cartItem.count))
    }

    override suspend fun getTotalOrderCost(): Double {
        return deliveryDao.getTotalOrderCost()
    }

    override suspend fun deleteCartItem(cartItem: CartItem) {
        deliveryDao.deleteCartItem(CartItemCache(cartItem.product.id, cartItem.count))
    }

    override suspend fun clearCart() {
        deliveryDao.clearCart()
    }

    override suspend fun createDelivery(delivery: Delivery): DeliveryResult {
        return try {
            service.createDelivery(
                DeliveryRequest(
                    delivery.street,
                    delivery.house,
                    delivery.products.map {
                        CartItemResponse(it.product.id, it.count)
                    }
                )
            )
            DeliveryResult.Success()
        } catch (e: HttpException) {
            DeliveryResult.Error(e.code() == 401)
        } catch (e: Exception) {
            DeliveryResult.Error(false)
        }
    }

    override suspend fun repeatOrder(order: List<CartItem>) {
        deliveryDao.repeatOrder(order.map { CartItemCache(it.product.id, it.count) })
    }
}