package com.radlance.fooddelivery.domain.usecase.catalog

import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.CatalogRepository

class SaveProductsUseCase(private val catalogRepository: CatalogRepository) {
    suspend operator fun invoke(productList: List<Product>) {
        catalogRepository.saveProducts(productList)
    }
}