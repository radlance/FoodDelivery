package com.radlance.fooddelivery.data.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.api.response.CategoryResponse
import com.radlance.fooddelivery.data.database.CategoryCache
import com.radlance.fooddelivery.data.database.ProductCache
import com.radlance.fooddelivery.data.database.ProductsDao
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.MainRepository

class MainRepositoryImpl(private val service: Service, private val productsDao: ProductsDao) :
    MainRepository {
    private var categories = listOf<CategoryResponse>()
    override suspend fun getProducts(): LoadResult {
        return try {
            categories = service.categories()
            val productList = service.products().map {
                Product(it.id, it.title, it.price, it.imageUrl, it.category.id, it.category.title)
            }
            LoadResult.Success(productList)
        } catch (e: Exception) {
            return LoadResult.Error
        }
    }

    override suspend fun saveProducts(productList: List<Product>) {
        productsDao.saveCategories(categories.map {
            CategoryCache(it.id, it.title)
        })
        productsDao.saveProducts(productList.map {
            ProductCache(it.id, it.title, it.price, it.imageUrl, it.categoryId)
        })
    }

    override suspend fun getLocalProducts(): List<Product> {
        return productsDao.getFullProductsInfo()
            .map {
                Product(
                    it.product.id,
                    it.product.title,
                    it.product.price,
                    it.product.imageUrl,
                    it.categoryId,
                    it.categoryTitle
                )
            }
    }

    override suspend fun getProductsByCategory(categoryName: String): List<Product> {
        return productsDao.getProductsByCategory(categoryName).map {
            Product(
                it.product.id,
                it.product.title,
                it.product.price,
                it.product.imageUrl,
                it.categoryId,
                it.categoryTitle
            )
        }
    }
}