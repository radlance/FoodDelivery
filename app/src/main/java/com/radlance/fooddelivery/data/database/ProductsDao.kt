package com.radlance.fooddelivery.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductsDao {
    @Query("SELECT * FROM product")
    suspend fun productList(): List<ProductCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducts(productList: List<ProductCache>)
}