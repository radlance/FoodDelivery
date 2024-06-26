package com.radlance.fooddelivery.presentation.core

import com.radlance.fooddelivery.domain.repository.AuthorizationRepository
import com.radlance.fooddelivery.domain.repository.CatalogRepository

interface ProvideRepository {
    fun authorizationRepository(): AuthorizationRepository
    fun catalogRepository(token: String): CatalogRepository
}