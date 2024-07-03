package com.radlance.fooddelivery.data.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.api.response.CategoryResponse
import com.radlance.fooddelivery.data.database.CartItemCache
import com.radlance.fooddelivery.data.database.CategoryCache
import com.radlance.fooddelivery.data.database.ProductCache
import com.radlance.fooddelivery.data.database.DeliveryDao
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.entity.Category
import com.radlance.fooddelivery.domain.entity.FullProduct
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.CatalogRepository

class CatalogRepositoryImpl(private val service: Service, private val deliveryDao: DeliveryDao) :
    CatalogRepository {
    private var categories = listOf<CategoryResponse>()
    override suspend fun getProducts(): LoadResult {
        return try {
            categories = service.categories()
            val productList = service.products().map {
                Product(it.id, it.title, it.price, it.imageUrl, it.category.id)
            }
            LoadResult.Success(productList)
        } catch (e: Exception) {
            return LoadResult.Error
        }
    }

    override suspend fun saveProducts(productList: List<Product>) {
        deliveryDao.saveCategories(categories.map {
            CategoryCache(it.id, it.title)
        })
        deliveryDao.saveProducts(productList.map {
            ProductCache(it.id, it.title, it.price, it.imageUrl, it.categoryId)
        })
    }

    override suspend fun getLocalProducts(): List<Product> {
        return deliveryDao.getProductsInfo().map {
            Product(it.id, it.title, it.price, it.imageUrl, it.categoryId)
        }
    }

    override suspend fun getProductsByCategory(categoryName: String): List<FullProduct> {
        return deliveryDao.getProductsByCategory(categoryName).map {
            FullProduct(
                Product(
                    it.product.id,
                    it.product.title,
                    it.product.price,
                    it.product.imageUrl,
                    it.product.categoryId
                ),
                Category(it.category.id, it.category.title)
            )
        }
    }

    override suspend fun searchProductsLikeName(query: String): List<Product> {
        return deliveryDao.searchProductsLikeName(query).map {
            Product(it.id, it.title, it.price, it.imageUrl, it.categoryId)
        }
    }

    override suspend fun addToCart(cartItem: CartItem) {
        deliveryDao.addToCart(CartItemCache(cartItem.count, cartItem.product.id))
    }
}