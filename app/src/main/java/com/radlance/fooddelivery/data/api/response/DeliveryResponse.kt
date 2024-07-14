package com.radlance.fooddelivery.data.api.response

import com.google.gson.annotations.SerializedName

data class DeliveryResponse(
    @SerializedName("productId") val productId: Int,
    @SerializedName("amount") val amount: Double
)
