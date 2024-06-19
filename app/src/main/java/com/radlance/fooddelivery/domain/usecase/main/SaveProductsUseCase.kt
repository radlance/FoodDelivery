package com.radlance.fooddelivery.domain.usecase.main

import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.MainRepository

class SaveProductsUseCase(private val mainRepository: MainRepository) {
    suspend operator fun invoke(productList: List<Product>) {
        mainRepository.saveProducts(productList)
    }
}