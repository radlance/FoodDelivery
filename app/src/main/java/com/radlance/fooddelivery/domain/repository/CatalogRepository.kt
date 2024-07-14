package com.radlance.fooddelivery.domain.repository

import com.radlance.fooddelivery.domain.core.LoadProductsResult
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.entity.FullProduct
import com.radlance.fooddelivery.domain.entity.Product

interface CatalogRepository {
    suspend fun loadProducts(): LoadProductsResult
    suspend fun getLocalProducts(): List<Product>
    suspend fun getProductsByCategory(categoryName: String): List<FullProduct>
    suspend fun searchProductsLikeName(query: String): List<Product>
    suspend fun addCartItem(cartItem: CartItem)
}