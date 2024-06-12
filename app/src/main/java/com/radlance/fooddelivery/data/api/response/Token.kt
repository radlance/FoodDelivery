package com.radlance.fooddelivery.data.api.response

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("token")
    val token: String
)
