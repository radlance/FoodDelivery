package com.radlance.fooddelivery.presentation.catalog.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.radlance.fooddelivery.domain.usecase.catalog.AddCartItemUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.GetLocalProductsUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.GetProductByCategoryUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.GetProductsUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.SaveProductsUseCase
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class ProductListViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[APPLICATION_KEY]
        val catalogRepository = (application as ProvideRepository).catalogRepository()

        val getProductsUseCase = GetProductsUseCase(catalogRepository)
        val saveProductsUseCase = SaveProductsUseCase(catalogRepository)
        val getLocalProductsUseCase = GetLocalProductsUseCase(catalogRepository)
        val getProductByCategoryUseCase = GetProductByCategoryUseCase(catalogRepository)
        val addCartItemUseCase = AddCartItemUseCase(catalogRepository)

        return ProductListViewModel(
            getProductsUseCase,
            saveProductsUseCase,
            getLocalProductsUseCase,
            getProductByCategoryUseCase,
            addCartItemUseCase
        ) as T
    }
}