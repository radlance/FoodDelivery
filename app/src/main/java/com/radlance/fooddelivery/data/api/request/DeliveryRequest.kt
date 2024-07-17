package com.radlance.fooddelivery.data.api.request

import com.google.gson.annotations.SerializedName
import com.radlance.fooddelivery.data.api.response.CartItemResponse

data class DeliveryRequest(
    @SerializedName("street") val street: String,
    @SerializedName("house") val house: Int,
    @SerializedName("products") val products: List<CartItemResponse>
)