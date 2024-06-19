package com.radlance.fooddelivery.domain.usecase.main

import com.radlance.fooddelivery.domain.core.ProductsResult
import com.radlance.fooddelivery.domain.repository.MainRepository

class GetProductsUseCase(private val mainRepository: MainRepository) {
    suspend operator fun invoke(): ProductsResult {
        return mainRepository.getProducts()
    }
}