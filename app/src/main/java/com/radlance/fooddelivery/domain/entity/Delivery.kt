package com.radlance.fooddelivery.domain.entity

data class Delivery(
    val street: String,
    val house: Int,
    val products: List<CartItem>
)