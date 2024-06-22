package com.radlance.fooddelivery.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class FullProductInfo(
    @Embedded
    val product: ProductCache,
    @ColumnInfo("categoryId")
    val categoryId: Long,
    @ColumnInfo(name = "categoryTitle")
    val categoryTitle: String
)