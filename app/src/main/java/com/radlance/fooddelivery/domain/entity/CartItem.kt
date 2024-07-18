package com.radlance.fooddelivery.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    var count: Int,
    val product: Product
): Parcelable