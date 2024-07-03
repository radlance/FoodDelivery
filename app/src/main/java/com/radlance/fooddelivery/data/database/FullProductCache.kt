package com.radlance.fooddelivery.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class FullProductCache(
    @Embedded val product: ProductCache,
    @Relation(
        parentColumn = "category",
        entityColumn = "id"
    )
    val category: CategoryCache
)