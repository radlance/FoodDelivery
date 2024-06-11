package com.radlance.fooddelivery.data.api.core

import com.radlance.fooddelivery.data.api.request.NewUser
import com.radlance.fooddelivery.data.api.request.UserData
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {

    @POST("/user/register")
    suspend fun registerUser(@Body newUser: NewUser)

    @POST("user/login")
    suspend fun loginUser(@Body userData: UserData): String

}