package com.radlance.fooddelivery.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class FullCartItemCache(
    @Embedded val cartItem: CartItemCache,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val product: ProductCache
)