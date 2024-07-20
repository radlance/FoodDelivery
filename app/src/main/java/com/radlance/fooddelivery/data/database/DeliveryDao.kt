package com.radlance.fooddelivery.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DeliveryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducts(productList: List<ProductCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategories(categoryCache: List<CategoryCache>)

    @Query("SELECT * FROM category WHERE id = :id")
    suspend fun getCategoryById(id: Int): CategoryCache

    @Query("SELECT * FROM product")
    suspend fun getProductsInfo(): List<ProductCache>

    @Query(
        "SELECT product.*, category.title AS category_title FROM product" +
                " INNER JOIN category ON product.category = category.id" +
                " WHERE category_title = :categoryName"
    )
    suspend fun getProductsByCategory(categoryName: String): List<FullProductCache>


    @Query("SELECT * FROM product WHERE title LIKE '%' || :query || '%'")
    suspend fun searchProductsLikeName(query: String): List<ProductCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCartItem(cartItem: CartItemCache)

    @Update
    suspend fun updateCartItem(cartItem: CartItemCache)

    @Query("SELECT count FROM cart_item WHERE id = :id")
    suspend fun getProductCountById(id: Int): Int

    @Query("SELECT * FROM cart_item INNER JOIN product ON cart_item.id = product.id")
    suspend fun getFullCartItemInfo(): List<FullCartItemCache>

    @Query(
        "SELECT SUM(count * price) AS total_price FROM cart_item" +
            " INNER JOIN product ON cart_item.id = product.id"
    )
    suspend fun getTotalOrderCost(): Double

    @Delete
    suspend fun deleteCartItem(cartItem: CartItemCache)

    @Query("DELETE FROM cart_item")
    suspend fun clearCart()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun repeatOrder(order: List<CartItemCache>)
}