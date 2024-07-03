package com.radlance.fooddelivery.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Long,
    val title: String,
    val price: Double,
    val imageUrl: String,
    val categoryId: Long
): Parcelable
