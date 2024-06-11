package com.radlance.fooddelivery.data.api.core

import com.radlance.fooddelivery.data.api.request.NewUser
import com.radlance.fooddelivery.data.api.request.UserData
import dagger.Provides
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Inject

interface Service {

    @POST("/user/register")
    suspend fun registerUser(@Body newUser: NewUser)

    @POST("user/login")
    suspend fun loginUser(@Body userData: UserData): String

}