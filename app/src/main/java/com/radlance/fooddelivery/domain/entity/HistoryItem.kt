package com.radlance.fooddelivery.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryItem(
    val id: Int,
    val orderTime: String,
    val street: String,
    val house: Int,
    val productDeliveries: List<CartItem>
): Parcelable