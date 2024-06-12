package com.radlance.fooddelivery.domain.entity

data class User(
    val login: String,
    val password: String,
    val fullName: String = "",
    val phoneNumber: String = ""
)