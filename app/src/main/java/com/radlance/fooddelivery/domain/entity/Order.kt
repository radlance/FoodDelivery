package com.radlance.fooddelivery.domain.entity

data class Order(
    val product: Product,
    val amount: Double
)