package com.radlance.fooddelivery.presentation.core

import com.radlance.fooddelivery.domain.repository.UserRepository

interface ProvideRepository {
    fun userRepository(): UserRepository
}