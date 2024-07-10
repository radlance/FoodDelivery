package com.radlance.fooddelivery.domain.usecase.catalog

import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.repository.CatalogRepository

class AddCartItemUseCase(private val repository: CatalogRepository) {
    suspend operator fun invoke(cartItem: CartItem) {
        repository.addCartItem(cartItem)
    }
}