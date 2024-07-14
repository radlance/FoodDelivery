package com.radlance.fooddelivery.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.api.response.CategoryResponse
import com.radlance.fooddelivery.data.database.CartItemCache
import com.radlance.fooddelivery.data.database.CategoryCache
import com.radlance.fooddelivery.data.database.DeliveryDao
import com.radlance.fooddelivery.data.database.ProductCache
import com.radlance.fooddelivery.domain.core.LoadProductsResult
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.entity.Category
import com.radlance.fooddelivery.domain.entity.FullProduct
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.CatalogRepository

class CatalogRepositoryImpl(private val service: Service, private val deliveryDao: DeliveryDao) :
    CatalogRepository {
    private var categories = listOf<CategoryResponse>()
    override suspend fun loadProducts(): LoadProductsResult {
        return try {
            categories = service.categories()
            val productList = service.products().map {
                Product(it.id, it.title, it.price, it.imageUrl, it.category.id)
            }
            saveProducts(productList)
            LoadProductsResult.Success(productList)
        } catch (e: Exception) {
            return LoadProductsResult.Error
        }
    }

    private suspend fun saveProducts(productList: List<Product>) {
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

    override suspend fun addCartItem(cartItem: CartItem) {
        try {
            deliveryDao.addCartItem(CartItemCache(cartItem.product.id, cartItem.count))
        } catch (e: SQLiteConstraintException) {
            val incrementedCount =
                deliveryDao.getProductCountById(cartItem.product.id) + cartItem.count
            if (incrementedCount < 100) {
                deliveryDao.updateCartItem(CartItemCache(cartItem.product.id, incrementedCount))
            } else {
                deliveryDao.updateCartItem(CartItemCache(cartItem.product.id, MAX_ITEM_COUNT))
            }
        }
    }

    companion object {
        private const val MAX_ITEM_COUNT = 99
    }
}