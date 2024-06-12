package com.radlance.fooddelivery.presentation.core

fun validateLogin(login: String): Boolean {
    val emailRegex =
        Regex("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
    return login.matches(emailRegex)
}

fun parseString(string: String?) = string?.trim() ?: ""