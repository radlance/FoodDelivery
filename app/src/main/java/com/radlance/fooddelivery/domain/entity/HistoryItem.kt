package com.radlance.fooddelivery.domain.entity

data class HistoryItem(
    val id: Int,
    val orderTime: String,
    val street: String,
    val house: Int,
    val building: Int,
    val apartment: Int,
    val productDeliveries: List<Order>
)