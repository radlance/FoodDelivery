package com.radlance.fooddelivery.domain.usecase.main

import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.repository.MainRepository

class GetProductsUseCase(private val mainRepository: MainRepository) {
    suspend operator fun invoke(): LoadResult {
        return mainRepository.getProducts()
    }
}