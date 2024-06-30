package com.radlance.fooddelivery.domain.usecase.catalog

import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.repository.CatalogRepository

class GetProductsUseCase(private val catalogRepository: CatalogRepository) {
    suspend operator fun invoke(): LoadResult {
        return catalogRepository.getProducts()
    }
}