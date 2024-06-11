package com.radlance.fooddelivery.domain.entity

data class User(
    val fullName: String,
    val login: String,
    val password: String,
    val phoneNumber: String
)