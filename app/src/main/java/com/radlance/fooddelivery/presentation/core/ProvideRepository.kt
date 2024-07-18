package com.radlance.fooddelivery.presentation.core

import com.radlance.fooddelivery.domain.repository.AuthorizationRepository
import com.radlance.fooddelivery.domain.repository.CatalogRepository
import com.radlance.fooddelivery.domain.repository.HistoryRepository
import com.radlance.fooddelivery.domain.repository.OrderRepository

interface ProvideRepository {
    fun authorizationRepository(): AuthorizationRepository
    fun catalogRepository(): CatalogRepository
    fun orderRepository(token: String? = null): OrderRepository
    fun historyRepository(token: String): HistoryRepository
}