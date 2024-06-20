package com.radlance.fooddelivery.domain.repository

import com.radlance.fooddelivery.domain.core.AuthResult
import com.radlance.fooddelivery.domain.entity.User

interface AuthorizationRepository {
    suspend fun registerUser(user: User): AuthResult
    suspend fun loginUser(user: User): AuthResult
}