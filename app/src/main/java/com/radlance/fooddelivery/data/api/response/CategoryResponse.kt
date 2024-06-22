package com.radlance.fooddelivery.data.api.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String
)