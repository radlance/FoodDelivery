package com.radlance.fooddelivery.presentation.catalog.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.radlance.fooddelivery.domain.usecase.main.GetLocalProductsUseCase
import com.radlance.fooddelivery.domain.usecase.main.GetProductByCategoryUseCase
import com.radlance.fooddelivery.domain.usecase.main.GetProductsUseCase
import com.radlance.fooddelivery.domain.usecase.main.SaveProductsUseCase
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class ProductListViewModelFactory(private val token: String = "") : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[APPLICATION_KEY]
        val mainRepository = (application as ProvideRepository).mainRepository(token)

        val getProductsUseCase = GetProductsUseCase(mainRepository)
        val saveProductsUseCase = SaveProductsUseCase(mainRepository)
        val getLocalProductsUseCase = GetLocalProductsUseCase(mainRepository)
        val getProductByCategoryUseCase = GetProductByCategoryUseCase(mainRepository)
        return ProductListViewModel(
            getProductsUseCase,
            saveProductsUseCase,
            getLocalProductsUseCase,
            getProductByCategoryUseCase
        ) as T
    }
}