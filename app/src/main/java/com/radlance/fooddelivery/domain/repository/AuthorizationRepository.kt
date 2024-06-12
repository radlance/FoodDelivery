package com.radlance.fooddelivery.domain.repository

import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.User

interface AuthorizationRepository {
    suspend fun registerUser(user: User): LoadResult
    suspend fun loginUser(user: User): LoadResult
}