package com.radlance.fooddelivery.domain.usecase

import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.User
import com.radlance.fooddelivery.domain.repository.AuthorizationRepository

class RegisterUserUseCase(private val repository: AuthorizationRepository) {
    suspend operator fun invoke(user: User): LoadResult {
        return repository.registerUser(user)
    }
}