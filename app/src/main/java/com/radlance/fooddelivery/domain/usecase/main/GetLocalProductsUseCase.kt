package com.radlance.fooddelivery.domain.usecase.main

import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.MainRepository

class GetLocalProductsUseCase(private val mainRepository: MainRepository) {
    suspend operator fun invoke(): List<Product> {
        return mainRepository.getLocalProducts()
    }
}