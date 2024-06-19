package com.radlance.fooddelivery.domain.usecase.authorization

import com.radlance.fooddelivery.domain.core.AuthResult
import com.radlance.fooddelivery.domain.entity.User
import com.radlance.fooddelivery.domain.repository.AuthorizationRepository

class LoginUserUseCase(private val repository: AuthorizationRepository) {
    suspend operator fun invoke(user: User): AuthResult {
        return repository.loginUser(user)
    }
}