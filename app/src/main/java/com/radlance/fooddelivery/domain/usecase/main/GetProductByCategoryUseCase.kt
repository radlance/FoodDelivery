package com.radlance.fooddelivery.domain.usecase.main

import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.MainRepository

class GetProductByCategoryUseCase(private val mainRepository: MainRepository) {
    suspend operator fun invoke(categoryName: String): List<Product> {
        return mainRepository.getProductsByCategory(categoryName)
    }
}