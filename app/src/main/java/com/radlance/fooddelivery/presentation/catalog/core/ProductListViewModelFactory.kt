package com.radlance.fooddelivery.presentation.catalog.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.radlance.fooddelivery.domain.usecase.catalog.AddCartItemUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.GetLocalProductsUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.GetProductByCategoryUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.LoadProductsUseCase
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class ProductListViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[APPLICATION_KEY]
        val catalogRepository = (application as ProvideRepository).catalogRepository()

        val loadProductsUseCase = LoadProductsUseCase(catalogRepository)
        val getLocalProductsUseCase = GetLocalProductsUseCase(catalogRepository)
        val getProductByCategoryUseCase = GetProductByCategoryUseCase(catalogRepository)
        val addCartItemUseCase = AddCartItemUseCase(catalogRepository)
        val mapper = LoadProductsResultMapper()

        return ProductListViewModel(
            loadProductsUseCase,
            getLocalProductsUseCase,
            getProductByCategoryUseCase,
            addCartItemUseCase,
            mapper
        ) as T
    }
}