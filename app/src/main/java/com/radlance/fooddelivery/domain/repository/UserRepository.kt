package com.radlance.fooddelivery.domain.repository

import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.User

interface UserRepository {
    suspend fun registerUser(user: User): LoadResult
}