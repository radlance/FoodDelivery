package com.radlance.fooddelivery.domain.usecase.catalog

import com.radlance.fooddelivery.domain.core.LoadProductsResult
import com.radlance.fooddelivery.domain.repository.CatalogRepository

class LoadProductsUseCase(private val catalogRepository: CatalogRepository) {
    suspend operator fun invoke(): LoadProductsResult {
        return catalogRepository.loadProducts()
    }
}