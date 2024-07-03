package com.radlance.fooddelivery.domain.usecase.catalog

import com.radlance.fooddelivery.domain.entity.FullProduct
import com.radlance.fooddelivery.domain.repository.CatalogRepository

class GetProductByCategoryUseCase(private val catalogRepository: CatalogRepository) {
    suspend operator fun invoke(categoryName: String): List<FullProduct> {
        return catalogRepository.getProductsByCategory(categoryName)
    }
}