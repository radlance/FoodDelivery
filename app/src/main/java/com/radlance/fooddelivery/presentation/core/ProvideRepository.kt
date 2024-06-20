package com.radlance.fooddelivery.presentation.core

import com.radlance.fooddelivery.domain.repository.AuthorizationRepository
import com.radlance.fooddelivery.domain.repository.MainRepository

interface ProvideRepository {
    fun authorizationRepository(): AuthorizationRepository
    fun mainRepository(token: String): MainRepository
}