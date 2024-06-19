package com.radlance.fooddelivery.domain.repository

import com.radlance.fooddelivery.domain.core.ProductsResult
import com.radlance.fooddelivery.domain.entity.Product

interface MainRepository {
    suspend fun getProducts(): ProductsResult
    suspend fun saveProducts(productList: List<Product>)
}