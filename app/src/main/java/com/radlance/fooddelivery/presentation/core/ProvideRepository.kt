package com.radlance.fooddelivery.presentation.core

import com.radlance.fooddelivery.domain.repository.AuthorizationRepository

interface ProvideRepository {
    fun authorizationRepository(): AuthorizationRepository
}