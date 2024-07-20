package com.radlance.fooddelivery.data.api.core

import com.radlance.fooddelivery.data.api.request.DeliveryRequest
import com.radlance.fooddelivery.data.api.request.NewUser
import com.radlance.fooddelivery.data.api.request.UserData
import com.radlance.fooddelivery.data.api.response.CategoryResponse
import com.radlance.fooddelivery.data.api.response.HistoryResponse
import com.radlance.fooddelivery.data.api.response.ProductResponse
import com.radlance.fooddelivery.data.api.response.Token
import com.radlance.fooddelivery.domain.entity.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Service {

    @POST("user/register")
    suspend fun registerUser(@Body newUser: NewUser)

    @POST("user/login")
    suspend fun loginUser(@Body userData: UserData): Token

    @GET("products")
    suspend fun products(): List<ProductResponse>

    @GET("products/{id}")
    suspend fun productById(@Path("id") id: Int): Product

    @GET("categories")
    suspend fun categories(): List<CategoryResponse>

    @GET("deliveries/user/history")
    suspend fun history(): List<HistoryResponse>

    @POST("client/deliveries/create")
    suspend fun createDelivery(@Body delivery: DeliveryRequest)
}