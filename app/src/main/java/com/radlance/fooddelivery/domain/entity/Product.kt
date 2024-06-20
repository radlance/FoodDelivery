package com.radlance.fooddelivery.domain.entity

data class Product(
    val id: Long,
    val title: String,
    val price: Double,
    val imageUrl: String,
)
