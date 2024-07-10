package com.radlance.fooddelivery.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_item", foreignKeys = [ForeignKey(
        entity = ProductCache::class,
        parentColumns = ["id"],
        childColumns = ["id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CartItemCache(
    @PrimaryKey @ColumnInfo(name = "id") val productId: Long,
    @ColumnInfo(name = "count") val count: Int
//    @PrimaryKey(autoGenerate = true) val id: Long? = null
)

//TODO сделать инкремент при наличии товара в корзине