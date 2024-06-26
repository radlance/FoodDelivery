package com.radlance.fooddelivery.domain.usecase.main

import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.CatalogRepository

class GetProductByCategoryUseCase(private val catalogRepository: CatalogRepository) {
    suspend operator fun invoke(categoryName: String): List<Product> {
        return catalogRepository.getProductsByCategory(categoryName)
    }
}