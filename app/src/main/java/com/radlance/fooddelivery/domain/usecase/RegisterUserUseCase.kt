package com.radlance.fooddelivery.domain.usecase

import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.User
import com.radlance.fooddelivery.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(user: User): LoadResult {
        return repository.registerUser(user)
    }
}