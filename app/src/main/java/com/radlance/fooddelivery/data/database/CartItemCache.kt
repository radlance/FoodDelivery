package com.radlance.fooddelivery.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_item", foreignKeys = [ForeignKey(
        entity = ProductCache::class,
        parentColumns = ["id"],
        childColumns = ["product_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CartItemCache(
    @ColumnInfo(name = "count") val count: Int,
    @ColumnInfo(name = "product_id") val productId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long? = null
)