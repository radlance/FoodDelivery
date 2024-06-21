package com.radlance.fooddelivery.domain.repository

import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.Product

interface MainRepository {
    suspend fun getProducts(): LoadResult
    suspend fun saveProducts(productList: List<Product>)
    suspend fun getLocalProducts(): List<Product>
}