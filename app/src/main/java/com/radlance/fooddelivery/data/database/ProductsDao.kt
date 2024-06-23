package com.radlance.fooddelivery.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducts(productList: List<ProductCache>)

    @Insert
    suspend fun saveCategories(categoryCache: List<CategoryCache>)

    @Query("SELECT * FROM category WHERE id = :id")
    suspend fun getCategoryById(id: Long): CategoryCache

    @Query(
        "SELECT product.*, category.id AS categoryId, category.title AS categoryTitle FROM product" +
                " INNER JOIN category ON product.category = category.id"
    )
    suspend fun getFullProductsInfo(): List<FullProductInfo>

    @Query(
        "SELECT product.*, category.id AS categoryId, category.title AS categoryTitle FROM product" +
                " INNER JOIN category ON product.category = category.id" +
                " WHERE categoryTitle = :categoryName"
    )
    suspend fun getProductsByCategory(categoryName: String): List<FullProductInfo>
}