package com.radlance.fooddelivery.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class FullProductInfo(
    @Embedded val product: ProductCache,
    @ColumnInfo("category_id") val categoryId: Long,
    @ColumnInfo(name = "category_title") val categoryTitle: String
)