package com.radlance.fooddelivery.data.api.request

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String
)
