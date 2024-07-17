package com.radlance.fooddelivery.data.api.request

import com.google.gson.annotations.SerializedName

data class NewUser(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String,
    @SerializedName("roleId") val roleId: Int,
    @SerializedName("dateOfBirth") val dateOfBirth: String
)