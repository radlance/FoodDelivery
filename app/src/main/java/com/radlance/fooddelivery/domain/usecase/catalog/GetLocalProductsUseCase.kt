package com.radlance.fooddelivery.domain.usecase.catalog

import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.CatalogRepository

class GetLocalProductsUseCase(private val catalogRepository: CatalogRepository) {
    suspend operator fun invoke(): List<Product> {
        return catalogRepository.getLocalProducts()
    }
}