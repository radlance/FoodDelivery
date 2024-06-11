package com.radlance.fooddelivery.domain.usecase

import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.User
import com.radlance.fooddelivery.domain.repository.UserRepository

class LoginUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: User): LoadResult {
        return repository.loginUser(user)
    }
}