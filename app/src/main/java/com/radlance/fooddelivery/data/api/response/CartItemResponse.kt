package com.radlance.fooddelivery.data.api.response

import com.google.gson.annotations.SerializedName

data class CartItemResponse(
    @SerializedName("productId") val productId: Int,
    @SerializedName("amount") val amount: Int
)
