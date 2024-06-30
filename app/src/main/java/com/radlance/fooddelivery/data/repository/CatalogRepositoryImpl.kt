package com.radlance.fooddelivery.data.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.api.response.CategoryResponse
import com.radlance.fooddelivery.data.database.CartItemCache
import com.radlance.fooddelivery.data.database.CategoryCache
import com.radlance.fooddelivery.data.database.FullProductInfo
import com.radlance.fooddelivery.data.database.ProductCache
import com.radlance.fooddelivery.data.database.ProductsDao
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.CatalogRepository

class CatalogRepositoryImpl(private val service: Service, private val productsDao: ProductsDao) :
    CatalogRepository {
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
        return mapFullInfoToProductList(productsDao.getFullProductsInfo())
    }

    override suspend fun getProductsByCategory(categoryName: String): List<Product> {
        return mapFullInfoToProductList(productsDao.getProductsByCategory(categoryName))
    }

    override suspend fun searchProductsLikeName(query: String): List<Product> {
        return mapFullInfoToProductList(productsDao.searchProductsLikeName(query))
    }

    override suspend fun addToCart(cartItem: CartItem) {
        productsDao.addToCart(CartItemCache(cartItem.count, cartItem.product.id))
    }

    private fun mapFullInfoToProductList(fullProductInfo: List<FullProductInfo>): List<Product> {
        return fullProductInfo.map {
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