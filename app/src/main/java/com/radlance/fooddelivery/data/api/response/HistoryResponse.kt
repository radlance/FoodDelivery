package com.radlance.fooddelivery.data.api.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("openingDateTime") val orderTime: String,
    @SerializedName("street") val street: String,
    @SerializedName("house") val house: Int,
    @SerializedName("productDeliveries") val productDeliveries: List<CartItemResponse>
)