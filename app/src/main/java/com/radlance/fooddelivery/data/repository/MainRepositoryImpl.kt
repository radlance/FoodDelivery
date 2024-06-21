package com.radlance.fooddelivery.data.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.database.ProductCache
import com.radlance.fooddelivery.data.database.ProductsDao
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.MainRepository

class MainRepositoryImpl(private val service: Service, private val productsDao: ProductsDao) :
    MainRepository {
    override suspend fun getProducts(): LoadResult {
        return try {
            val productList =
                service.products().map { Product(it.id, it.title, it.price, it.imageUrl) }
            LoadResult.Success(productList)
        } catch (e: Exception) {
            return LoadResult.Error
        }
    }

    override suspend fun saveProducts(productList: List<Product>) {
        productsDao.saveProducts(productList.map {
            ProductCache(it.title, it.price, it.imageUrl, it.id)
        })
    }

    override suspend fun getLocalProducts(): List<Product> {
        return productsDao.productList().map { Product(it.id, it.title, it.price, it.imageUrl) }
    }
}