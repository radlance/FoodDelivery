package com.radlance.fooddelivery.domain.usecase.main

import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.CatalogRepository

class SearchProductsLikeNameUseCase(private val catalogRepository: CatalogRepository) {
    suspend operator fun invoke(query: String): List<Product> {
        return catalogRepository.searchProductsLikeName(query)
    }
}